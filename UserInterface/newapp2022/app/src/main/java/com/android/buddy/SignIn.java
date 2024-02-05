package com.android.buddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignIn extends AppCompatActivity {
    private FirebaseAuth mAuth = null;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private FirebaseUser user;
    private FirebaseFirestore db;
    private Button signup_bt;
    private Button signin_bt;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();

        signup_bt = (Button) findViewById(R.id.signup_bt);
        EditText username = (EditText) findViewById(R.id.username);
        EditText password = (EditText) findViewById(R.id.password);

        signup_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSignUp = new Intent(SignIn.this, SignUp.class);
                startActivity(intentSignUp);
            }
        });

        signin_bt = (Button) findViewById(R.id.signin_bt);
        signin_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!username.getText().toString().equals("") && !password.getText().toString().equals("")) {
                    logIn(username.getText().toString(), password.getText().toString());
                } else {
                    Toast.makeText(SignIn.this, "계정과 비밀번호를 입력하세요.", Toast.LENGTH_LONG).show();
                }
            }
        });

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(SignIn.this, AppMain.class);
                    intent.putExtra("logInEmail", username.getText().toString());
                    startActivity(intent);
                    finish();
                } else {
                }
            }
        };
    }


    public void logIn(String username, String password) {
        mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(SignIn.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                    mAuth.addAuthStateListener(firebaseAuthListener);
                } else {
                    Toast.makeText(SignIn.this, "아이디 또는 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();
        if(user != null) {
            startActivity(new Intent(this, AppMain.class));
            finish();
        }
        Intent get = getIntent();
        int num = get.getIntExtra("again", 0);
        if(num == 1) {
            mAuth.signInWithEmailAndPassword(get.getStringExtra("email"), get.getStringExtra("password")).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        mAuth.addAuthStateListener(firebaseAuthListener);
                    } else {
                    }
                }
            });
        }

    }


    /*@Override
    public void onRestart(){
        super.onRestart();
        if(user != null) {
            startActivity(new Intent(this, AppMain.class));
            finish();
        }
    }*/

    protected void onStop() {
        super.onStop();
        if (firebaseAuthListener != null) {
            mAuth.removeAuthStateListener(firebaseAuthListener);
        }
    }

}