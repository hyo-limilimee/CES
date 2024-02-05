package com.android.buddy;

import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.EventListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class FriendSearch extends AppCompatActivity {
    private  FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private FirebaseUser user;
    private String uid; //현재 로그인 계정의 uid
    private EditText searchEmail;
    private TextView showEmail;
    private Button searchBtn;
    private Button requestBtn;
    private String email; //검색할 이메일
    private String friendEmail; //현재 로그인 계정의 이메일
    private String friendName; //현재 로그인 계정의 이름
    private String friendUid; //친구 신청을 보낼 친구의 uid

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_search);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        uid = user.getUid();

        searchEmail = findViewById(R.id.searchEmail);
        showEmail = findViewById(R.id.showEmail);
        searchBtn = findViewById(R.id.searchBtn);
        requestBtn = findViewById(R.id.requestBtn);

        db.collection("users").document(uid).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    friendEmail = snapshot.getString("email");
                    friendName = snapshot.getString("name");
                } else {

                }
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = searchEmail.getText().toString();
                search(email);
            }
        });

        requestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> friendInfo = new HashMap<>();
                friendInfo.put("friendName", friendName); //현재 로그인 계정의 이름
                friendInfo.put("friendEmail", friendEmail); //현재 로그인 계정의 이메일
                friendInfo.put("friendUid", uid); //현재 로그인 계정의 uid
                friendInfo.put("status", "request");
                friendInfo.put("heart", 0);

                db.collection("users").document(friendUid).collection("friends").document(uid).set(friendInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(FriendSearch.this, "친구 신청을 보냈습니다.", Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
            }
        });
    }

    public void search(String email) {
        db.collection("users")
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                showEmail.setText(document.getString("email"));
                                friendUid = document.getString("uid");
                                showEmail.setVisibility(View.VISIBLE);
                                requestBtn.setVisibility(View.VISIBLE);
                                break;
                            }
                            if(showEmail.getVisibility() != View.VISIBLE)
                                Toast.makeText(FriendSearch.this, "존재하지 않는 계정입니다.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(FriendSearch.this, "task 실패", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}