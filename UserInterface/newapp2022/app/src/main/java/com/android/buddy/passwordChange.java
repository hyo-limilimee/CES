package com.android.buddy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;

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

public class passwordChange extends AppCompatActivity {
    private  FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private FirebaseUser user;
    private String uid;
    private TextView nowPassword;
    private EditText changePassword;
    private Button modifyBtn;
    private String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        uid = user.getUid();

        nowPassword = findViewById(R.id.nowPassword);
        changePassword = findViewById(R.id.changePassword);

        db.collection("users").document(uid).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    nowPassword.setText(snapshot.getString("password"));
                } else {

                }
            }
        });

        modifyBtn = findViewById(R.id.modifyPassword);
        modifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password = changePassword.getText().toString();
                changePassword(password);
            }
        });

    }
    public void changePassword(String password) {
        user.updatePassword(password)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            db.collection("users").document(uid).update("password", password).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(passwordChange.this, "비밀번호 변경 성공", Toast.LENGTH_LONG).show();
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(passwordChange.this, "비밀번호 변경 실패", Toast.LENGTH_LONG).show();
                                        }
                                    });
                        }
                        else {
                            Toast.makeText(passwordChange.this, "task 실패", Toast.LENGTH_LONG).show();
                        }
                    }
                });
        /* user.updatePassword(password).addOnCompleteListener(passwordChange.this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    db.collection("users").document(uid)
                            .update("password", password)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(passwordChange.this, "비밀번호 변경 성공", Toast.LENGTH_LONG).show();
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(passwordChange.this, "비밀번호 변경 실패", Toast.LENGTH_LONG).show();
                                }
                            });
                }
                else {
                    Toast.makeText(passwordChange.this, "task 실패", Toast.LENGTH_LONG).show();
                }
            }
        }); */
    }

}