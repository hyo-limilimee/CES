package com.ssu.bilda.presentation.sign

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ssu.bilda.data.enums.Department
import com.ssu.bilda.data.remote.App
import com.ssu.bilda.data.remote.RetrofitImpl
import com.ssu.bilda.data.remote.UserSharedPreferences
import com.ssu.bilda.data.remote.request.SignInRequest
import com.ssu.bilda.data.remote.response.AuthorizedResponse
import com.ssu.bilda.data.remote.response.BaseResponse
import com.ssu.bilda.data.service.UserService
import com.ssu.bilda.databinding.ActivitySignInBinding
import com.ssu.bilda.presentation.BnvActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInActivity : AppCompatActivity() {

    // Retrofit을 사용하여 API 호출
    private val userService = RetrofitImpl.nonRetrofit.create(UserService::class.java)

    private var userEmail: String? = null
    private var userPw: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 회원가입 하러가기 버튼 클릭
        binding.tvSigninMove.setOnClickListener {
            val intent = Intent(this, TermsActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        // 로그인 버튼 클릭
        binding.btnSignin.setOnClickListener {
            userEmail = binding.etSigninEmail.text.toString()
            userPw = binding.etSigninPw.text.toString()
            signIn(userEmail!!, userPw!!)
        }
    }


    val name = intent.getStringExtra("name") ?: ""
    val studentId = intent.getStringExtra("student_id") ?: ""
    val nickname = intent.getStringExtra("nickname") ?: ""
    val departmentName = intent.getStringExtra("department") ?: ""

    // Department enum으로 변환
    val department = Department.valueOf(departmentName)

    // 로그인 api 호출
    private fun signIn(userEmail: String, userPw: String) {
        val signInInfo = SignInRequest(userEmail, userPw) //로그인 시 필요한 정보

        val call: Call<BaseResponse<AuthorizedResponse>> = userService.signIn(signInInfo)
        call.enqueue(object : Callback<BaseResponse<AuthorizedResponse>> {
            override fun onResponse(
                call: Call<BaseResponse<AuthorizedResponse>>,
                response: Response<BaseResponse<AuthorizedResponse>>
            ) {
                if (response.isSuccessful) {
                    val baseResponse = response.body()
                    if (baseResponse?.success == true && baseResponse.code == 200) {
                        // 인증 성공 처리
                        Log.d("SignIn", "로그인 성공")

                        // 유저 정보 SH 저장
                        UserSharedPreferences.setUserEmail(this@SignInActivity, userEmail)
                        UserSharedPreferences.setUserPw(this@SignInActivity, userPw)
                        UserSharedPreferences.setUserName(this@SignInActivity, name)
                        UserSharedPreferences.setUserNickname(this@SignInActivity, nickname)
                        UserSharedPreferences.setUserStId(this@SignInActivity, studentId)
                        UserSharedPreferences.setUserDep(this@SignInActivity, department)

                        // 토큰 저장
                        val accessToken = baseResponse.result?.accessToken
                        val refreshToken = baseResponse.result?.refreshToken

                        accessToken?.let {
                            App.token_prefs.accessToken = it
                        }

                        refreshToken?.let {
                            App.token_prefs.refreshToken = it
                        }

                        // 홈 화면 이동
                        moveToHome()
                    } else {
                        Log.d("SignIn", "로그인 실패")
                        showToast("다시 시도해주세요")
                    }
                }
            }

            override fun onFailure(call: Call<BaseResponse<AuthorizedResponse>>, t: Throwable) {
                Log.e("SignIn", "네트워크 오류: ${t.message}")
                showToast("네트워크 오류")
            }
        })
    }

    private fun showToast(message: String) {
        runOnUiThread {
            Toast.makeText(this@SignInActivity, message, Toast.LENGTH_SHORT).show()
        }
    }

    // 홈 화면 이동
    private fun moveToHome() {
        val mIntent = Intent(this@SignInActivity, BnvActivity::class.java)
        mIntent.putExtra("loadFragment", "home") // HomeFragment를 로드하기 위한 신호 전달
        startActivity(mIntent)
        finish()
    }
}
