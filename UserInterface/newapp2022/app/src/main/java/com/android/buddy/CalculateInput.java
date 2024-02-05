package com.android.buddy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class CalculateInput extends AppCompatActivity {
    EditText dividend;
    EditText divisor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_input);
    }

    public void onBtnResult(View view){
        dividend = findViewById(R.id.dividend);
        divisor = findViewById(R.id.divisor);
        int result = Integer.parseInt(dividend.getText().toString())/Integer.parseInt(divisor.getText().toString());
        Intent intent = new Intent(this, CalculateResult.class);
        intent.putExtra("CalResult", result);
        startActivity(intent);
        finish();
    }
}