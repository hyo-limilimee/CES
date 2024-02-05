package com.android.buddy;

import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.EventListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class FriendManage extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<User> userArrayList;
    Adapter adapter;
    FirebaseFirestore db;
    FirebaseUser user;
    FirebaseAuth mAuth;
    String uid;
    ProgressDialog progressDialog;
    String friendUid = null;
    String myName = null;
    String myEmail = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_manage);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        uid = user.getUid();

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("데이터 불러오는 중... ");
        progressDialog.show();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        uid = currentUser.getUid();

        userArrayList = new ArrayList<>();
        adapter = new Adapter(FriendManage.this, userArrayList);

        recyclerView.setAdapter(adapter);

        EventChangeListener();

        adapter.setOnItemClickListener(new Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                String name = userArrayList.get(position).getFriendName();
                String email = userArrayList.get(position).getFriendEmail();

                Toast.makeText (FriendManage.this, "이름 : " + name + "\n이메일 : " + email, Toast.LENGTH_SHORT).show ();
            }

            @Override
            public void onAcceptClick(View v, int position) {
                String name = userArrayList.get(position).getFriendName();
                String email = userArrayList.get(position).getFriendEmail();

                db.collection("users")
                        .whereEqualTo("email", email)
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value,
                                                @Nullable FirebaseFirestoreException e) {
                                if (e != null) {
                                    return;
                                }

                                List<String> cities = new ArrayList<>();
                                for (QueryDocumentSnapshot doc : value) {
                                    friendUid = doc.getString("uid");
                                }
                            }
                        });

                db.collection("users")
                        .whereEqualTo("uid", uid)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        myName = document.getString("name");
                                        myEmail = document.getString("email");
                                        break;
                                    }
                                }
                            }
                        });

                if(friendUid == null||myEmail==null||myName==null)
                    Toast.makeText (FriendManage.this, "다시 시도하세요.", Toast.LENGTH_SHORT).show ();
                else
                    acceptFriend(name, friendUid);
            }

            @Override
            public void onRejectClick(View v, int position) {
                String name = userArrayList.get(position).getFriendName();
                String email = userArrayList.get(position).getFriendEmail();

                db.collection("users")
                        .whereEqualTo("email", email)
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value,
                                                @Nullable FirebaseFirestoreException e) {
                                if (e != null) {
                                    return;
                                }

                                List<String> cities = new ArrayList<>();
                                for (QueryDocumentSnapshot doc : value) {
                                    friendUid = doc.getString("uid");
                                }
                            }
                        });

                if(friendUid == null)
                    Toast.makeText (FriendManage.this, "다시 시도하세요.", Toast.LENGTH_SHORT).show ();
                else
                    rejectFriend(name, friendUid);
            }
        });
    }

    private void EventChangeListener() {

        //db.collection("users").document(uid).collection("friends").orderBy("name", Query.Direction.ASCENDING)
        db.collection("users").document(uid).collection("friends").whereEqualTo("status", "request").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    if(progressDialog.isShowing())
                        progressDialog.dismiss();

                    Log.e("Firestore error", error.getMessage());
                    return;
                }
                for(DocumentChange dc : value.getDocumentChanges()) {
                    if(dc.getType() == DocumentChange.Type.ADDED) {
                        userArrayList.add(dc.getDocument().toObject(User.class));
                    }

                    adapter.notifyDataSetChanged();
                    if(progressDialog.isShowing())
                        progressDialog.dismiss();

                }
                if(progressDialog.isShowing()) {
                    progressDialog.dismiss();
                    Toast.makeText(FriendManage.this, "친구 신청이 없습니다.", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void acceptFriend(String friendName, String friendEmail) {

        Map<String, Object> friendInfo = new HashMap<>();
        friendInfo.put("friendName", myName); // 친구신청받은 계정의 이름
        friendInfo.put("friendEmail", myEmail); //친구신청받은 계정의 이메일
        friendInfo.put("friendUid", uid); //친구신청받은 계정의 uid
        friendInfo.put("status", "accept");
        friendInfo.put("heart", 0);


        db.collection("users").document(friendUid).collection("friends").document(uid).set(friendInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        db.collection("users").document(uid).collection("friends").document(friendUid).update("status", "accept").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(FriendManage.this, "친구 요청을 수락하였습니다.", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });

        finish();
    }

    public void rejectFriend(String friendName, String friendEmail) {
        db.collection("users").document(uid).collection("friends").document(friendUid).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(FriendManage.this, "친구 요청을 거절하였습니다.", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });

        finish();
    }
}