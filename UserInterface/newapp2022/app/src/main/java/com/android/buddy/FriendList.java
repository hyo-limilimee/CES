package com.android.buddy;

import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.EventListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FriendList extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<User> userArrayList;
    listAdapter adapter;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    String uid;
    ProgressDialog progressDialog;
    ImageView firstHeart;
    ImageView secondHeart;
    ImageView thirdHeart;
    String friendUid;
    TextView nameInfo;
    TextView emailInfo;
    TextView deleteDialog;
    AlertDialog dialog;
    Long heart;
    Long num;
    Long friendNum;
    Long changeHeart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);
        firstHeart = findViewById(R.id.firstHeart);
        secondHeart = findViewById(R.id.secondHeart);
        thirdHeart = findViewById(R.id.thirdHeart);

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
        friendUid = null;
        changeHeart = null;
        heart = null;

        userArrayList = new ArrayList<>();
        adapter = new listAdapter(FriendList.this, userArrayList);

        adapter.setOnItemClickListener(new listAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                String name = userArrayList.get(position).getFriendName();
                String email = userArrayList.get(position).getFriendEmail();
                Intent getIntent = getIntent();
                String date = getIntent.getStringExtra("Date");

                db.collection("users")
                        .whereEqualTo("email", email)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        friendUid = document.getString("uid");
                                        break;
                                    }
                                } else {
                                    Toast.makeText(FriendList.this, "task 실패", Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                if(friendUid != null) {
                    db.collection("users")
                            .document(uid).collection("friends").document(friendUid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if(task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        changeHeart = document.getLong("heart");
                                    }
                                }
                            });
                    if(changeHeart != null) {
                        changeHeart += 1;
                        db.collection("users").document(uid).collection("friends").document(friendUid).update("heart", changeHeart).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                            }
                        });
                        db.collection("users").document(friendUid).collection("friends").document(uid).update("heart", changeHeart).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                            }
                        });

                        db.collection("users").document(uid).collection("calendar").document(date).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if(task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        friendNum = document.getLong("friend_num");
                                        friendNum++;
                                        db.collection("users").document(uid).collection("calendar").document(date).update("friend"+String.valueOf(friendNum), name).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                            }
                                        });
                                        db.collection("users").document(uid).collection("calendar").document(date).update("friend_num", friendNum).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                            }
                                        });
                                    }
                                }
                            }
                        });
                        /*Intent intent = new Intent(FriendList.this, CalendarInfo.class);
                        intent.putExtra("DATE", date);*/
                        finish();
                        Toast.makeText (FriendList.this, "친구 등록 완료.", Toast.LENGTH_SHORT).show();
                        //startActivity(intent);
                    }
                    else {
                        Toast.makeText(FriendList.this, "다시 시도하세요.", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(FriendList.this, "다시 시도하세요.", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onDeleteClick(View v, int position) {
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

                                for (QueryDocumentSnapshot doc : value) {
                                    friendUid = doc.getString("uid");
                                }
                            }
                        });

                if(friendUid == null)
                    Toast.makeText (FriendList.this, "다시 시도하세요.", Toast.LENGTH_SHORT).show ();
                else
                    deleteFriend();
            }
            @Override
            public void onInfoClick(View v, int position) {
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

                                for (QueryDocumentSnapshot doc : value) {
                                    friendUid = doc.getString("uid");
                                }
                            }
                        });

                if(friendUid == null)
                    Toast.makeText (FriendList.this, "다시 시도하세요.", Toast.LENGTH_SHORT).show ();
                else
                    infoShow(name, email);
            }
        });

        recyclerView.setAdapter(adapter);

        EventChangeListener();

        //friendNum.setText(String.valueOf(num));
    }

    private void EventChangeListener() {
        //db.collection("users").document(uid).collection("friends").orderBy("name", Query.Direction.ASCENDING)
        db.collection("users").document(uid).collection("friends").whereEqualTo("status", "accept").addSnapshotListener(new EventListener<QuerySnapshot>() {
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
                    Toast.makeText(FriendList.this, "친구가 없습니다.", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    public void deleteFriend() {
        db.collection("users").document(uid).collection("friends").document(friendUid).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });

        db.collection("users").document(friendUid).collection("friends").document(uid).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(FriendList.this, "친구 삭제 완료", Toast.LENGTH_LONG).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
        friendUid = null;

    }
    public void infoShow (String friendName, String friendEmail) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.friend_dialog, null, false);
        builder.setView(view);

        dialog = builder.create();

        nameInfo = view.findViewById(R.id.nameInfo);
        emailInfo = view.findViewById(R.id.emailInfo);
        deleteDialog = view.findViewById(R.id.deleteDialog);
        firstHeart = view.findViewById(R.id.firstHeart);
        secondHeart = view.findViewById(R.id.secondHeart);
        thirdHeart = view.findViewById(R.id.thirdHeart);

        nameInfo.setText(friendName);
        emailInfo.setText(friendEmail);

        db.collection("users").document(friendUid).collection("friends").document(uid).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    heart = snapshot.getLong("heart");
                } else {

                }
            }
        });



        if(heart != null) {
            if (heart>=0&&heart<5){
                firstHeart.setImageResource(R.drawable.ic_baseline_favorite_empty);
                secondHeart.setImageResource(R.drawable.ic_baseline_favorite_empty);
                thirdHeart.setImageResource(R.drawable.ic_baseline_favorite_empty);
            }
            else if(heart>=5&&heart<10) {
                firstHeart.setImageResource(R.drawable.ic_baseline_favorite_full);
                secondHeart.setImageResource(R.drawable.ic_baseline_favorite_empty);
                thirdHeart.setImageResource(R.drawable.ic_baseline_favorite_empty);
            }
            else if(heart>=10&&heart<20) {
                firstHeart.setImageResource(R.drawable.ic_baseline_favorite_full);
                secondHeart.setImageResource(R.drawable.ic_baseline_favorite_full);
                thirdHeart.setImageResource(R.drawable.ic_baseline_favorite_empty);
            }
            else if(heart>=20) {
                firstHeart.setImageResource(R.drawable.ic_baseline_favorite_full);
                secondHeart.setImageResource(R.drawable.ic_baseline_favorite_full);
                thirdHeart.setImageResource(R.drawable.ic_baseline_favorite_full);
            }

            deleteDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    friendUid = null;
                    dialog.dismiss();
                }
            });

            dialog.show();
            heart = null;
        }
        else
            Toast.makeText(FriendList.this, "다시 시도하세요.", Toast.LENGTH_SHORT).show();


    }
    public void onManageBtn(View view){
        Intent intent = new Intent(this, FriendManage.class);
        startActivity(intent);
    }

    public void onSearchBtn(View view){
        Intent intent = new Intent(this, FriendSearch.class);
        startActivity(intent);
    }
}