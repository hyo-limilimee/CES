package com.ssu.bilda.presentation.mypage

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.ssu.bilda.R
import com.ssu.bilda.data.remote.RetrofitImpl
import com.ssu.bilda.data.remote.request.ChangePasswordRequest
import com.ssu.bilda.data.remote.response.BaseResponse
import com.ssu.bilda.data.remote.response.ChangePasswordResponse
import com.ssu.bilda.data.service.UserService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        val llArrowAndChangePw: View = findViewById(R.id.ll_ic_arrow_and_change_pw)
        val newPasswordEditText: TextInputEditText = findViewById(R.id.et_change_pw)
        val confirmPasswordEditText: TextInputEditText = findViewById(R.id.et_change_repw)
        val changePasswordButton: FrameLayout = findViewById(R.id.fl_ic_pw_change_completed_btn)

        // 뒤로가기 버튼 설정
        llArrowAndChangePw.setOnClickListener {
            onBackPressed()
        }

        // 비밀번호 변경 버튼 클릭 이벤트
        changePasswordButton.setOnClickListener {
            val newPassword = newPasswordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()

            // 비밀번호 변경 요청
            changePassword(newPassword, confirmPassword)
        }
    }

    private fun changePassword(newPassword: String, confirmPassword: String) {
        // TODO: 서버와의 통신을 통한 비밀번호 변경 로직
        // RetrofitImpl.authenticatedRetrofit을 사용하여 API 호출
        val userService = RetrofitImpl.authenticatedRetrofit.create(UserService::class.java)
        val changePasswordRequest = ChangePasswordRequest(newPassword)

        userService.changePassword(changePasswordRequest).enqueue(object : Callback<BaseResponse<ChangePasswordResponse>> {
            override fun onResponse(
                call: Call<BaseResponse<ChangePasswordResponse>>,
                response: Response<BaseResponse<ChangePasswordResponse>>
            ) {
                if (response.isSuccessful) {
                    // 비밀번호 변경 성공
                    Log.d("비밀번호 변경 성공", "서버 응답: ${response.body()?.message}")
                    // TODO: 성공에 대한 UI 처리 또는 화면 전환 등 추가 로직 수행
                } else {
                    // 비밀번호 변경 실패
                    Log.d("비밀번호 변경 실패", "서버 응답: ${response.errorBody()?.string()}")
                    // TODO: 실패에 대한 UI 처리 또는 사용자에게 알림 등 추가 로직 수행
                }
            }

            override fun onFailure(
                call: Call<BaseResponse<ChangePasswordResponse>>,
                t: Throwable
            ) {
                // 네트워크 또는 기타 오류 처리
                Log.e("비밀번호 변경 실패", "네트워크 에러 또는 기타 오류: ${t.message}")
                // TODO: 오류에 대한 UI 처리 또는 사용자에게 알림 등 추가 로직 수행
            }
        })
    }
}
