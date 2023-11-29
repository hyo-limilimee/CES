package com.ssu.bilda.presentation.sign

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.ssu.bilda.R
import com.ssu.bilda.data.remote.RetrofitImpl
import com.ssu.bilda.data.remote.request.VerifyEmailRequest
import com.ssu.bilda.data.remote.response.BaseResponse
import com.ssu.bilda.data.service.EmailService
import com.ssu.bilda.databinding.ActivityEmailInputBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EmailInputActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmailInputBinding
    private var countDownTimer: CountDownTimer? = null

    // Retrofit을 이용한 이메일 전송 서비스 생성
    private val retrofitWithoutToken = RetrofitImpl.nonRetrofit
    private val emailService = retrofitWithoutToken.create(EmailService::class.java)

    // 이메일 인증 여부를 나타내는 변수
    private var isEmailCertified = false

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
            sendEmail(email)
        }

        // "인증 확인" 버튼 클릭 시 이벤트 처리
        binding.btnSignupCheckauth.setOnClickListener {
            val email = binding.etSignupEmail.text.toString()
            val authCode = binding.etSignupAuth.text.toString()
//            certifyEmail(email, authCode)
        }

        // "인증코드 재전송" 버튼 클릭 시 이벤트 처리
        binding.tvSignupResend.setOnClickListener {
            countDownTimer?.cancel() // 타이머 취소
            val email = binding.etSignupEmail.text.toString()
//            resendEmail(email)
        }

        // 다음 버튼 클릭
        binding.btnSignupEmailnext.setOnClickListener {
            val intent = Intent(this, PwInputActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }

    // 타이머 시작
    private fun startTimer() {
        val startTimeMillis = 5 * 60 * 1000 // 3 minutes in milliseconds
        countDownTimer = object : CountDownTimer(startTimeMillis.toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = millisUntilFinished / (60 * 1000)
                val seconds = (millisUntilFinished % (60 * 1000)) / 1000
                binding.tvSignupTime.visibility = View.VISIBLE
                binding.tvSignupTime.text = String.format("%02d:%02d", minutes, seconds)
            }

            override fun onFinish() {
                binding.tvSignupTime.text = "00:00" // Timer finished
            }
        }.start()
    }


    // flag
    // 0 : 인증코드 전송 관련
    // 1 : 인증 성공 여부
    private fun showEmailDialog(text: String, flag: Int, isSuccess: Boolean) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_email, null)

        val builder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)

        val alertDialog = builder.create()
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val titleDialog = dialogView.findViewById<TextView>(R.id.tv_signup_dialog_guide)
        titleDialog.text = text

        val btnDialog = dialogView.findViewById<AppCompatButton>(R.id.btn_signup_dialog)
        btnDialog.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.show()

        btnDialog.setOnClickListener {
            alertDialog.dismiss()
            when (flag) {
                0 -> {
                    if (isSuccess) {
                        countDownTimer?.cancel()
                        // 타이머를 시작하는 동작
                        startTimer()
                    } else {
                        // 타이머 시작 실패에 대한 처리
                    }
                }

                1 -> {
                    if (isSuccess) {
                        // 인증 성공에 대한 처리
                        binding.btnSignupEmailnext.isEnabled = true
                        countDownTimer?.cancel()
                    } else {
                        // 인증 실패에 대한 처리
                    }
                }
            }
        }


        val window: Window? = alertDialog.window
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        window?.setGravity(Gravity.CENTER)
    }

    // 이메일 전송 API 호출
    private fun sendEmail(email: String) {
        val verifyEmailRequest = VerifyEmailRequest(email)
        emailService.sendEmail(verifyEmailRequest).enqueue(object : Callback<BaseResponse<Void>> {
            override fun onResponse(
                call: Call<BaseResponse<Void>>,
                response: Response<BaseResponse<Void>>
            ) {
                if (response.isSuccessful) {
                    val code = response.body()?.code
                    if (code == 200) {
                        // 성공한 경우
                        Log.d("SendEmail", "이메일 전송 성공")
                        showEmailDialog("인증코드 전송완료", 0, true)
                    } else {
                        // 실패한 경우
                        Log.d("SendEmail", "이메일 전송 실패 / code = $code")
                    }
                }
            }

            override fun onFailure(call: Call<BaseResponse<Void>>, t: Throwable) {
                // 네트워크 에러 처리
                Log.d("SendEmailDebug", "네트워크 오류: ${t.message}")
            }
        })
    }

//    // 이메일 인증 API 호출
//    private fun certifyEmail(email: String, code: String) {
//        emailService.certifyEmail(email, code).enqueue(object : Callback<String> {
//            override fun onResponse(call: Call<String>, response: Response<String>) {
//                if (response.isSuccessful) {
//                    // 인증 성공한 경우
//                    Log.d("CertifyEmail", "이메일 인증 성공")
//                    showEmailDialog("이메일 인증 성공", 1, true)
//
//                    // 이메일 인증이 완료되었으므로 버튼 활성화 및 상태 변경
//                    isEmailCertified = true
//                    binding.btnSignupEmailnext.isEnabled = true
//                    countDownTimer?.cancel()
//                } else {
//                    // 인증 실패한 경우
//                    Log.d("CertifyEmail", "이메일 인증 실패")
//                    showEmailDialog("이메일 인증 실패", 1, false)
//                }
//            }
//
//            override fun onFailure(call: Call<String>, t: Throwable) {
//                // 네트워크 에러 처리
//                Log.d("SendEmail", "네트워크 오류: " + t.message.toString())
//            }
//        })
//    }

//    // 이메일 재전송 API 호출
//    private fun resendEmail(email: String) {
//        emailService.sendEmail(email).enqueue(object : Callback<String> {
//            override fun onResponse(call: Call<String>, response: Response<String>) {
//                if (response.isSuccessful) {
//                        // 성공한 경우
//                        Log.d("ResendEmailDebug", "이메일 재전송 성공")
//                        showEmailDialog("인증코드 재전송 성공",0,true)
//                    } else {
//                        // 실패한 경우
//                        Log.d("ResendEmailDebug", "이메일 재전송 실패")
//                        showEmailDialog("이메일 재전송 실패",0,false)
//                    }
//                }
//
//                override fun onFailure(call: Call<String>, t: Throwable) {
//                // 네트워크 에러 처리
//                Log.d("ResendEmailDebug", "네트워크 오류: " + t.message.toString())
//            }
//        })
//    }
}
