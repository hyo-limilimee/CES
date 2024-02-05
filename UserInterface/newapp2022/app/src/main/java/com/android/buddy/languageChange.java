package com.android.buddy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import java.util.Locale;

public class languageChange extends AppCompatActivity {
    private static final String TAG = "languageChange";
    RadioButton korean, english;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_change);

        korean = findViewById(R.id.radioButton);
        english = findViewById(R.id.radioButton2);

        korean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Locale locale = new Locale("ko");
                Locale.setDefault(locale);

                Configuration configuration = new Configuration();
                configuration.locale = locale;

                SharedPreferences sharedPreference;
                sharedPreference = getSharedPreferences("sharedPreference", Activity.MODE_PRIVATE);


                languageChange.this.getResources().updateConfiguration(configuration, languageChange.this.getResources().getDisplayMetrics());

                SharedPreferences.Editor editor;
                editor = sharedPreference.edit();
                editor.putString("Lang", "ko");
                editor.apply();

                Intent intent = languageChange.this.getPackageManager().getLaunchIntentForPackage(languageChange.this.getPackageName());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                languageChange.this.finish();
                startActivity(intent);
            }
        });


        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Locale locale = new Locale("en");
                Locale.setDefault(locale);

                Configuration configuration = new Configuration();
                configuration.locale = locale;

                SharedPreferences sharedPreference;
                sharedPreference = getSharedPreferences("sharedPreference", Activity.MODE_PRIVATE);


                languageChange.this.getResources().updateConfiguration(configuration, languageChange.this.getResources().getDisplayMetrics());

                SharedPreferences.Editor editor;
                editor = sharedPreference.edit();
                editor.putString("Lang", "en");
                editor.apply();

                Intent intent = languageChange.this.getPackageManager().getLaunchIntentForPackage(languageChange.this.getPackageName());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                languageChange.this.finish();

                startActivity(intent);
            }
        });
    }
}