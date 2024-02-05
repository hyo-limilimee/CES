package com.android.buddy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

public class AppMain extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button logout;
    private FirebaseFirestore db;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_main);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        uid = user.getUid();

        if(user != null){
            db.collection("users").document(uid).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot snapshot,
                                    @Nullable FirebaseFirestoreException e) {
                    if (e != null) {
                        return;
                    }

                    if (snapshot != null && snapshot.exists()) {
                    } else {
                        Intent intent = getIntent();
                        DocumentReference docRef = db.collection("users").document(intent.getStringExtra("logInEmail"));
                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        editUserInfo(intent.getStringExtra("logInEmail"));
                                    } else {
                                    }
                                } else {
                                }
                            }
                        });
                    }
                }
            });
        } else{
            /*Intent intentSign = new Intent(AppMain.this, SignIn.class);
            startActivity(intentSign); */
        }

        logout = (Button) findViewById(R.id.logoutBtn);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent intent = new Intent(AppMain.this, SignIn.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void editUserInfo(String email) {
        db.collection("users").document(email).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    Map<String, Object> user = new HashMap<>();
                    user.put("uid", uid);
                    user.put("email", email);
                    user.put("password", snapshot.getString("password"));
                    user.put("name", snapshot.getString("name"));
                    user.put("phone", snapshot.getString("phone"));

                    db.collection("users").document(uid)
                            .set(user, SetOptions.merge())
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                }
                            });

                    db.collection("users").document(email).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                } else {

                }
            }
        });
    }

    public void onBtnCalendar(View view){
        Intent intent = new Intent(this, CalendarMain.class);
        startActivity(intent);
    }

    public void onBtnMyPage(View view){
        Intent intent = new Intent(this, MyPageMain.class);
        startActivity(intent);
    }

    public void onBtnCalculator(View view){
        Intent intent = new Intent(this, CalculateInput.class);
        startActivity(intent);
    }
    public void onBtnFriend(View view){
        Intent intent = new Intent(this, FriendList.class);
        startActivity(intent);
    }

    public void onBtnPlace(View view){
        Intent intent = new Intent(this, map.class);
        startActivity(intent);
    }

}