package com.ssu.bilda.presentation.sign

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.ssu.bilda.data.remote.RetrofitImpl
import com.ssu.bilda.data.remote.response.BaseResponse
import com.ssu.bilda.data.remote.response.Hresponse
import com.ssu.bilda.data.service.UserService
import com.ssu.bilda.databinding.ActivityTermsOfPrivacyBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TermsOfPrivacyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTermsOfPrivacyBinding
    private val userService: UserService = RetrofitImpl.nonRetrofit.create(UserService::class.java)
    val call = userService.getPrivacyPolicy()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTermsOfPrivacyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 개인정보정책 api 호출
        userService.getPrivacyPolicy().enqueue(object : Callback<BaseResponse<String>> {
            override fun onResponse(
                call: Call<BaseResponse<String>>,
                response: Response<BaseResponse<String>>
            ) {
                if (response.isSuccessful) {
                    val baseResponse: BaseResponse<String>? = response.body()
                    if (baseResponse != null) {
                        val content: String? = baseResponse.result
                        if (content != null) {
                            binding.tvPrivacyTermsContent.text = content
                        } else {
                            Log.e("Policy", "응답 content가 null입니다.")
                        }
                    } else {
                        Log.e("Policy", "응답이 null입니다.")
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("Policy", "응답 실패")
                }
            }

            override fun onFailure(call: Call<BaseResponse<String>>, t: Throwable) {
                Log.e("Policy", "API호출 실패")
            }
        })

        // 버튼 클릭으로 되돌아가기
        binding.btnPrivacyTermsBack.setOnClickListener {
            finish()
        }
        // 버튼이 담긴 영역 클릭으로 되돌아가기
        binding.flPrivacyTermsBack.setOnClickListener {
            finish()
        }
    }
}