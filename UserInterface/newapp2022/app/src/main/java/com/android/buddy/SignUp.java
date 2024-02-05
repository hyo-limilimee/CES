package com.android.buddy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button submit_bt;
    private Button cancel_bt;
    private EditText put_id;
    private EditText put_password;
    private EditText put_name;
    private EditText put_phone;
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        put_id = findViewById(R.id.put_id);
        put_password = findViewById(R.id.put_password);
        put_name = findViewById(R.id.put_username);
        put_phone = findViewById(R.id.put_phone);

        submit_bt = findViewById(R.id.submit_bt);
        submit_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!put_id.getText().toString().isEmpty()&&!put_password.getText().toString().isEmpty()){
                    addUserInfo(put_id.getText().toString(), put_password.getText().toString(), put_name.getText().toString(), put_phone.getText().toString());
                    createUser(put_id.getText().toString(), put_password.getText().toString(), put_name.getText().toString(), put_phone.getText().toString());
                }
                else{
                    Toast.makeText(SignUp.this, "계정과 비밀번호를 입력해주세요.", Toast.LENGTH_LONG).show();
                }
            }
        });

        cancel_bt = findViewById(R.id.cancel_bt);
        cancel_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        /* CheckBox checkbox1 = findViewById(R.id.checkbox1);
        checkbox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) buttonView.setText("개인정보 수집에 동의합니다.");
                else buttonView.setText("개인정보 수집에 동의하지 않습니다");

            }
        });

        CheckBox checkbox2 = findViewById(R.id.checkbox2);
        checkbox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) buttonView.setText("GPS 위치정보 수집에 동의합니다.");
                else buttonView.setText("GPS 위치정보 수집에 동의하지 않습니다.");

            }
        });

        CheckBox checkbox3 = findViewById(R.id.checkbox3);
        checkbox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) buttonView.setText("푸쉬 알림 수신에 동의합니다.");
                else buttonView.setText("푸쉬 알림 수신에 동의하지 않습니다.");

            }
        }); */

    }

    public void addUserInfo(String email, String password, String name, String phone) {

        Map<String, Object> user = new HashMap<>();
        user.put("email", email);
        user.put("password", password);
        user.put("name", name);
        user.put("phone", phone);

        db.collection("users").document(email)
                .set(user)
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
    }

    public void createUser (String email, String password, String name, String phone) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //Toast.makeText(SignUp.this, "Fehler"+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                if(task.isSuccessful()){
                    Toast.makeText(SignUp.this, "회원가입 성공", Toast.LENGTH_LONG).show();
                   /*Intent intent = new Intent(SignUp.this, SignIn.class);
                    startActivity(intent); */
                    finish();
                }
                else {
                    Toast.makeText(SignUp.this, "이미 존재하는 계정입니다.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}