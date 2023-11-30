package com.ssu.bilda.presentation.sign

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.ssu.bilda.R
import com.ssu.bilda.databinding.ActivityMyInfoInputBinding

class MyInfoInputActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyInfoInputBinding
    private var isFirstSelection = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyInfoInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val majors = resources.getStringArray(R.array.major_array)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, majors)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerSignupMajor.adapter = adapter
        binding.spinnerSignupMajor.setSelection(adapter.getPosition("컴퓨터학부"))

        binding.spinnerSignupMajor.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (!isFirstSelection) {
                        val selectedItem = parent?.getItemAtPosition(position).toString()
                        Toast.makeText(
                            applicationContext,
                            "선택한 학과 : $selectedItem",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        isFirstSelection = false
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    if (!isFirstSelection) {
                        Toast.makeText(applicationContext, "학과를 선택해주세요.", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        binding.btnSignupEnd.setOnClickListener {
            val name = binding.etSignupName.text.toString()
            val studentId = binding.etSignupStudentid.text.toString()
            val nickname = binding.etSignupNickname.text.toString()
            val major = binding.spinnerSignupMajor.selectedItem.toString() // 선택된 학과

            if (name.isNotEmpty() && studentId.isNotEmpty() && nickname.isNotEmpty()) {
                showConfirmDialog()
            } else {
                Toast.makeText(this, "모든 정보를 입력해주세요", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // 학번, 이름 확인 관련 다이얼로그
    private fun showConfirmDialog(
    ) {
        val alertDialog: AlertDialog = AlertDialog.Builder(this@MyInfoInputActivity)
            .setIcon(R.drawable.ic_notice)
            .setMessage("입력한 이름과 학번은 추후 수정이 불가합니다.\n 회원가입 완료로 넘어 가시겠어요?")
            .setPositiveButton("네") { dialog, _ ->
                dialog.dismiss()
                // SignInActivity로 이동
                val intent = Intent(this@MyInfoInputActivity, SignInActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)

                finish()
            }
            .setNegativeButton("아니오") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        alertDialog.setTitle("확인 필요")
        alertDialog.setCancelable(false)
        alertDialog.show()
    }
}


