package com.ssu.bilda.presentation.sign

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
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
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.ssu.bilda.R
import com.ssu.bilda.data.remote.RetrofitImpl
import com.ssu.bilda.data.remote.request.VerifyAuthCodeRequest
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

    // 인증코드 재전송 횟수 카운트
    private var resendEmailCount = 1

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

        // 인증코드 전송버튼 클릭
        binding.btnSignupSendauth.setOnClickListener {
            val email = binding.etSignupEmail.text.toString()
            sendEmail(email)
        }

        // 학교메일열기 버튼클릭
        binding.tvSignupOpenmail.setOnClickListener{
            val url = "https://gw.ssu.ac.kr/o365Userlogin.aspx"

            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)

            // 웹 브라우저로 열기
            startActivity(intent)

        }

        // "인증 확인" 버튼 클릭 시 이벤트 처리
        binding.btnSignupCheckauth.setOnClickListener {
            val email = binding.etSignupEmail.text.toString()
            val authCode = binding.etSignupAuth.text.toString()
            certifyEmail(email, authCode)
        }

        // "인증코드 재전송" 버튼 클릭 시 이벤트 처리
        binding.tvSignupResend.setOnClickListener {
            countDownTimer?.cancel() // 타이머 취소
            val email = binding.etSignupEmail.text.toString()
            resendEmail(email)
        }

        // 다음 버튼 클릭
        binding.btnSignupEmailnext.setOnClickListener {
            // 사용자가 입력한 이메일 값
            val userEmail = binding.etSignupEmail.text.toString()

            // 다음 액티비티로 전달할 Intent 생성
            val intent = Intent(this, PwInputActivity::class.java)

            // 사용자 이메일 값을 다음 액티비티로 전달
            intent.putExtra("user_email", userEmail) //보낸 값 getIntent().getStringExtra("user_email") 로 뽑아쓰기

            // 액티비티 시작
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


    // flag관련
    // 0 : 인증 코드 전송 관련
    // 1 : 인증 성공 여부
    private fun showEmailDialog(
        text: String,
        subtext: String,
        sendtime: String,
        flag: Int,
        isSuccess: Boolean
    ) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_email, null)

        val builder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)

        val alertDialog = builder.create()
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val titleDialog = dialogView.findViewById<TextView>(R.id.tv_signup_dialog_guide)
        titleDialog.text = text
        val subDialog = dialogView.findViewById<TextView>(R.id.tv_signup_dialog_resend1)
        subDialog.text = subtext
        val sendTimeDialog = dialogView.findViewById<TextView>(R.id.tv_signup_dialog_resend2)
        sendTimeDialog.text = sendtime


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
        val verifyEmailRequest = VerifyEmailRequest(email = email)
        binding.btnSignupSendauth.isEnabled = false // 한 번 클릭 후 버튼 비활성화
        emailService.sendEmail(verifyEmailRequest).enqueue(object : Callback<BaseResponse<Void>> {
            override fun onResponse(
                call: Call<BaseResponse<Void>>,
                response: Response<BaseResponse<Void>>
            ) {
                if (response.isSuccessful) {
                    val code = response.body()?.code
                    if (code == 200) {
                        // 성공한 경우
                        Log.d("SendEmail", "인증코드 발급 성공")
                        showEmailDialog("인증코드 전송 완료.", "재전송 가능 횟수 : ", "2", 0, true)
                        binding.btnSignupCheckauth.isEnabled = true //인증코드 전송 1번 후 재전송 버튼 활성화
                    } else {
                        // 실패한 경우
                        Log.d("SendEmail", "인증코드 발급 실패")
                        Toast.makeText(this@EmailInputActivity, "다시 시도해주세요", Toast.LENGTH_SHORT)
                            .show()
                        binding.btnSignupSendauth.isEnabled = true // 오류로 인한 실패 시 버튼 다시 활성화
                    }
                }
            }

            override fun onFailure(call: Call<BaseResponse<Void>>, t: Throwable) {
                // 네트워크 에러 처리
                Log.d("SendEmail", "네트워크 오류: " + t.message.toString())
                Toast.makeText(this@EmailInputActivity, "네트워크 오류", Toast.LENGTH_SHORT).show()
                binding.btnSignupSendauth.isEnabled = true // 오류로 인한 실패 시 버튼 다시 활성화
            }
        })
    }

    // 이메일 인증 API 호출
    private fun certifyEmail(email: String, authCode:String) {
        val verifyAuthCodeRequest = VerifyAuthCodeRequest(email,authCode)
        emailService.certifyEmail(verifyAuthCodeRequest).enqueue(object : Callback<BaseResponse<Void>> {
                override fun onResponse(
                    call: Call<BaseResponse<Void>>,
                    response: Response<BaseResponse<Void>>
                ) {
                    if (response.isSuccessful) {
                        val code = response.body()?.code
                        if (code == 200) {
                            // 인증 성공 처리
                            Log.d("CertifyEmail", "이메일 인증 성공")
                            showEmailDialog("이메일 인증 성공", " ", " ", 1, true)
                            // 이메일 인증이 완료되었으므로 버튼 활성화 및 상태 변경
                            isEmailCertified = true
                            binding.btnSignupEmailnext.isEnabled = true
                            countDownTimer?.cancel()
                        } else {
                            // 인증 실패 처리
                            Log.d("CertifyEmail", "이메일 인증 실패")
                            Toast.makeText(this@EmailInputActivity, "인증코드 확인 후 재입력 해주세요", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                override fun onFailure(call: Call<BaseResponse<Void>>, t: Throwable) {
                    // 네트워크 에러 처리
                    Log.d("CertifyEmail", "네트워크 오류: " + t.message.toString())
                    Toast.makeText(this@EmailInputActivity, "네트워크 오류", Toast.LENGTH_SHORT).show()

                }
            })
    }

    // 인증코드 재전송 api 호출
    private fun resendEmail(email: String) {
        if (resendEmailCount >= -2) {
            val verifyEmailRequest = VerifyEmailRequest(email = email)
            if (resendEmailCount != -1) {
                emailService.sendEmail(verifyEmailRequest)
                    .enqueue(object : Callback<BaseResponse<Void>> {
                        override fun onResponse(
                            call: Call<BaseResponse<Void>>,
                            response: Response<BaseResponse<Void>>
                        ) {
                            if (response.isSuccessful) {
                                val code = response.body()?.code
                                if (code == 200) {
                                    // 성공한 경우
                                    Log.d("reSendEmail", "인증코드 재전송 성공")
                                    val sendTime = resendEmailCount.toString() // 남은 전송 횟수
                                    if (resendEmailCount == 1) {
                                        showEmailDialog(
                                            "인증코드 재전송 완료",
                                            "재전송 가능 횟수: ",
                                            "$sendTime",
                                            0,
                                            true
                                        )
                                        resendEmailCount -= 1 // 재전송 횟수 감소
                                    } else if (resendEmailCount == 0) {
                                        showEmailDialog(
                                            "인증코드 재전송 완료",
                                            "재전송 가능 횟수: ",
                                            "$sendTime",
                                            0,
                                            true
                                        )
                                        resendEmailCount -= 1 // 재전송 횟수 감소
                                    } else {
                                        // 실패한 경우
                                        Log.d("reSendEmail", "인증코드 재전송 실패")
                                        Toast.makeText(this@EmailInputActivity,"다시 시도해주세요", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        }

                        override fun onFailure(call: Call<BaseResponse<Void>>, t: Throwable) {
                            // 네트워크 에러 처리
                            Log.d("reSendEmail", "네트워크 오류: " + t.message.toString())
                            Toast.makeText(this@EmailInputActivity, "네트워크 오류", Toast.LENGTH_SHORT)
                                .show()
                        }
                    })
            } else {
                showEmailDialog("인증코드 전송 횟수 초과", "5분 후 다시 시도", "", 0, false)
            }
        }
    }
}



