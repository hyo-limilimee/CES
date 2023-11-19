package com.ssu.bilda

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MyInfoInputActivity : AppCompatActivity() {
    private lateinit var spinnerSignupMajor: Spinner
    private var isFirstSelection = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_info_input)

        spinnerSignupMajor = findViewById(R.id.spinner_signup_major)

        val majors = resources.getStringArray(R.array.major_array)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, majors)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerSignupMajor.adapter = adapter

        // 기본값 = 컴퓨터학부
        spinnerSignupMajor.setSelection(adapter.getPosition("컴퓨터학부"))

        // spinner 관련
        spinnerSignupMajor.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (!isFirstSelection) {
                    val selectedItem = parent?.getItemAtPosition(position).toString()
                    Toast.makeText(applicationContext, "선택한 학과: $selectedItem", Toast.LENGTH_SHORT).show()
                } else {
                    isFirstSelection = false
                }
            }
            // spinner 아무것도 선택 안된 경우
            override fun onNothingSelected(parent: AdapterView<*>?) {
                if (!isFirstSelection) {
                    Toast.makeText(applicationContext, "학과를 선택해주세요.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
