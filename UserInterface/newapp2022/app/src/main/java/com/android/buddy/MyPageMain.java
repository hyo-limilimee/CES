package com.android.buddy;

import androidx.annotation.Nullable;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.EventListener;

import androidx.appcompat.app.AppCompatActivity;

public class MyPageMain extends AppCompatActivity {
    private  FirebaseAuth mAuth;
    private TextView name;
    private TextView phone;
    private TextView password;
    private TextView email;
    private FirebaseFirestore db;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page_main);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        uid = user.getUid();

        name = findViewById(R.id.Name);
        phone = findViewById(R.id.Phone);
        password = findViewById(R.id.Password);
        email = findViewById(R.id.Email);

        infoShow();
    }
    public void infoShow() {
        db.collection("users").document(uid).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    name.setText(snapshot.getString("name"));
                    phone.setText(snapshot.getString("phone"));
                    password.setText(snapshot.getString("password"));
                    email.setText(snapshot.getString("email"));
                } else {

                }
            }
        });
    }
    public void onBtnModify(View view){
        Intent intent = new Intent(this, passwordChange.class);
        startActivity(intent);
    }

    public void onBtnlanguage(View view){
        Intent intent = new Intent(this, languageChange.class);
        startActivity(intent);
    }

    public void onBtndarkmode(View view){
        Intent intent = new Intent(this, DarkModeChange.class);
        startActivity(intent);
    }
}