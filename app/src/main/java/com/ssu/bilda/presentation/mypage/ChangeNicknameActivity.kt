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
import com.ssu.bilda.data.remote.request.ChangeNicknameRequest
import com.ssu.bilda.data.remote.response.BaseResponse
import com.ssu.bilda.data.remote.response.ChangeNicknameResponse
import com.ssu.bilda.data.service.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangeNicknameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_nickname)

        val llArrowAndChangeNickname: View = findViewById(R.id.ll_ic_arrow_and_change_nickname)
        val etNickname: TextInputEditText = findViewById(R.id.et_reset_nickname)
        val changeCompletedBtn: FrameLayout = findViewById(R.id.fl_blue_nickname_change_completed_btn)

        llArrowAndChangeNickname.setOnClickListener {
            // MyInfoFragment로 돌아가기
            onBackPressed()
        }

        changeCompletedBtn.setOnClickListener {
            // 닉네임 변경 버튼 클릭 시
            val newNickname = etNickname.text.toString()

            if (newNickname.isNotEmpty()) {
                // Retrofit을 사용하여 서버에 닉네임 변경 요청
                changeNickname(newNickname)
            } else {
                // 사용자에게 닉네임을 입력하라는 메시지 표시
                Toast.makeText(this, "닉네임을 입력하세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun changeNickname(newNickname: String) {
        val retrofit = RetrofitImpl.authenticatedRetrofit
        val userService = retrofit.create(UserService::class.java)

        val changeNicknameRequest = ChangeNicknameRequest(newNickname)

        userService.changeNickname(changeNicknameRequest)
            .enqueue(object : Callback<BaseResponse<ChangeNicknameResponse>> {
                override fun onResponse(
                    call: Call<BaseResponse<ChangeNicknameResponse>>,
                    response: Response<BaseResponse<ChangeNicknameResponse>>
                ) {
                    if (response.isSuccessful) {
                        // 성공적으로 서버 응답을 받았을 때의 처리
                        val changeNicknameResponse = response.body()?.result
                        Log.d("닉네임 변경 성공", "서버 응답: $changeNicknameResponse")
                        // 성공 처리 로직 추가
                        // 예: 닉네임 변경 완료 메시지 표시
                        Toast.makeText(
                            this@ChangeNicknameActivity,
                            "닉네임이 성공적으로 변경되었습니다.",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        // 서버 응답이 실패인 경우의 처리
                        Log.d("닉네임 변경 실패", "서버 응답 코드: ${response.code()}")
                        // 실패 처리 로직 추가
                        // 예: 닉네임 변경 실패 메시지 표시
                        Toast.makeText(
                            this@ChangeNicknameActivity,
                            "닉네임 변경에 실패했습니다. 다시 시도해주세요.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<BaseResponse<ChangeNicknameResponse>>, t: Throwable) {
                    // 네트워크 오류 또는 예외 처리
                    Log.e("닉네임 변경 오류", "에러 메시지: ${t.message}")
                    // 예외 처리 로직 추가
                    // 예: 닉네임 변경 실패 메시지 표시
                    Toast.makeText(
                        this@ChangeNicknameActivity,
                        "닉네임 변경에 실패했습니다. 다시 시도해주세요.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

}
