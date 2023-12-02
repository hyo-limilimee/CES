package com.ssu.bilda.presentation.mypage

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.ssu.bilda.R
import com.ssu.bilda.data.remote.RetrofitImpl
import com.ssu.bilda.data.remote.UserSharedPreferences
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
        if (newPassword == confirmPassword) {
            val retrofit = RetrofitImpl.authenticatedRetrofit
            val userService = retrofit.create(UserService::class.java)

            val changePasswordRequest = ChangePasswordRequest(newPassword)

            userService.changePassword(changePasswordRequest).enqueue(object : Callback<BaseResponse<ChangePasswordResponse>> {
                override fun onResponse(
                    call: Call<BaseResponse<ChangePasswordResponse>>,
                    response: Response<BaseResponse<ChangePasswordResponse>>
                ) {
                    if (response.isSuccessful) {
                        // 비밀번호 변경 성공
                        Log.d("비밀번호 변경 성공", "서버 응답: ${response.body()?.message}")

                        // 토스트 메시지로 성공 알림 표시
                        showToast("비밀번호가 성공적으로 변경되었습니다.")

                        // SharedPreferences에 변경된 비밀번호 저장
                        UserSharedPreferences.setUserNickname(
                            this@ChangePasswordActivity,
                            newPassword
                        )
                    } else {
                        // 비밀번호 변경 실패
                        Log.d("비밀번호 변경 실패", "서버 응답: ${response.errorBody()?.string()}")

                        // 토스트 메시지로 실패 알림 표시
                        showToast("비밀번호 변경에 실패했습니다. 다시 시도해주세요.")

                        // TODO: 실패에 대한 추가적인 UI 처리 또는 사용자에게 알림 등을 수행할 수 있습니다.
                    }
                }

                override fun onFailure(
                    call: Call<BaseResponse<ChangePasswordResponse>>,
                    t: Throwable
                ) {
                    // 네트워크 또는 기타 오류 처리
                    Log.e("비밀번호 변경 실패", "네트워크 에러 또는 기타 오류: ${t.message}")

                    // 토스트 메시지로 오류 알림 표시
                    showToast("네트워크 오류 또는 기타 오류로 인해 비밀번호 변경에 실패했습니다.")

                    // TODO: 오류에 대한 추가적인 UI 처리 또는 사용자에게 알림 등을 수행할 수 있습니다.
                }
            })
        } else {
            // 새 비밀번호와 확인용 비밀번호가 일치하지 않는 경우
            // 토스트 메시지로 사용자에게 알림 표시
            showToast("새 비밀번호와 확인용 비밀번호가 일치하지 않습니다. 다시 입력해주세요.")

            // TODO: 사용자에게 일치하지 않음을 알리는 추가적인 UI 처리 등을 수행할 수 있습니다.
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this@ChangePasswordActivity, message, Toast.LENGTH_SHORT).show()
    }

}
