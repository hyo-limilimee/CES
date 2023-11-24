package com.ssu.bilda.presentation.sign

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.ssu.bilda.BuildConfig
import com.ssu.bilda.R
import com.ssu.bilda.data.remote.RetrofitImpl
import com.ssu.bilda.data.service.EmailService
import com.ssu.bilda.databinding.ActivityEmailInputBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EmailInputActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmailInputBinding

    // Retrofit을 이용한 이메일 전송 서비스 생성
    private val retrofitWithoutToken = RetrofitImpl.nonRetrofit
    private val emailService = retrofitWithoutToken.create(EmailService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmailInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvSignupError.visibility = View.INVISIBLE
        binding.btnSignupSendauth.isEnabled = false

        binding.etSignupEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val text = s.toString()
                val isEmailValid = text.endsWith("@soongsil.ac.kr")
                binding.btnSignupSendauth.isEnabled = isEmailValid
                if (!isEmailValid && text.contains("@")) {
                    val newText = "$text" + "soongsil.ac.kr"
                    binding.etSignupEmail.setText(newText)
                    binding.etSignupEmail.setSelection(newText.length)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        // 인증코드전송버튼 클릭
        binding.btnSignupSendauth.setOnClickListener {
            val email = binding.etSignupEmail.text.toString()
            emailService.sendEmail(email).enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        val code = response.body()
                        Log.d("SendEmailDebug", "이메일 전송 성공")
                    } else {
                        // 실패한 경우
                        Log.d("SendEmailDebug", "이메일 전송 실패")
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    // 네트워크 에러 처리
                    Log.d("SendEmail", "네트워크 오류: " + t.message.toString())
                }
            })
            showEmailDialog()
        }

        // 다음 버튼 클릭
        binding.btnSignupEmailnext.setOnClickListener {
            val intent = Intent(this, PwInputActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }

    private fun showEmailDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_email, null)

        val builder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)

        val alertDialog = builder.create()
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val btnDialog = dialogView.findViewById<AppCompatButton>(R.id.btn_signup_dialog)
        btnDialog.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.show()

        val window: Window? = alertDialog.window
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        window?.setGravity(Gravity.CENTER)
    }
}
