package com.ssu.bilda.presentation.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ssu.bilda.R
import com.ssu.bilda.data.remote.response.BaseResponse
import com.ssu.bilda.data.remote.RetrofitImpl
import com.ssu.bilda.data.remote.UserSharedPreferences
import com.ssu.bilda.data.remote.response.SignInResponse
import com.ssu.bilda.data.service.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // API 호출
        val userService = RetrofitImpl.authenticatedRetrofit.create(UserService::class.java)
        val call = userService.getUserHome()

        call.enqueue(object : Callback<BaseResponse<SignInResponse>> {
            override fun onResponse(
                call: Call<BaseResponse<SignInResponse>>,
                response: Response<BaseResponse<SignInResponse>>
            ) {
                if (response.isSuccessful) {
                    val code = response.body()?.code
                    if (code == 200) {
                        Log.d("homefragment", "유저정보 불러오기 성공")
                        val signInResponse: SignInResponse? = response.body()?.result
                        signInResponse?.let { signIn ->
                            val name = signIn.name
                            val nickname = signIn.nickname
                            val studentId = signIn.studentId
                            val department = signIn.department

                            // SharedPreferences에 유저 정보 저장
                            UserSharedPreferences.setUserName(requireContext(), name)
                            UserSharedPreferences.setUserNickname(requireContext(), nickname)
                            UserSharedPreferences.setUserStId(requireContext(), studentId)
                            department?.let {
                                UserSharedPreferences.setUserDep(requireContext(), it)
                            }
                        }
                    } else {
                        Log.d("homefragment", "유저정보불러오기 실패")
                    }
                } else {
                    // 오류 처리
                }
            }

            override fun onFailure(call: Call<BaseResponse<SignInResponse>>, t: Throwable) {
                Log.e("homefragment", "네트워크 오류: ${t.message}")
            }
        })
    }
}

