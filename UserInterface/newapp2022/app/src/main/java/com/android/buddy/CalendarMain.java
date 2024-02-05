package com.android.buddy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.annotation.NonNull;

import android.content.Intent;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

public class CalendarMain extends AppCompatActivity {
    public CalendarView calendarView;
    public Button cha_Btn, del_Btn, save_Btn, getImage;
    public TextView diaryTextView, textView2, textView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_main);
        calendarView = findViewById(R.id.calendarView);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                Intent intent = new Intent(CalendarMain.this, CalendarInfo.class);
                String date = year + "." + (month + 1) + "." + dayOfMonth;
                intent.putExtra("DATE", date);

                startActivity(intent);
            }
        });
    }
}