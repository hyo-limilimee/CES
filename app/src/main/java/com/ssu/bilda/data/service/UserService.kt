package com.ssu.bilda.data.service

import com.ssu.bilda.data.remote.request.ChangeNicknameRequest
import com.ssu.bilda.data.remote.request.ChangePasswordRequest
import com.ssu.bilda.data.remote.request.SignInRequest
import com.ssu.bilda.data.remote.request.SignUpRequest
import com.ssu.bilda.data.remote.response.AuthorizedResponse
import com.ssu.bilda.data.remote.response.BaseResponse
import com.ssu.bilda.data.remote.response.ChangeNicknameResponse
import com.ssu.bilda.data.remote.response.ChangePasswordResponse
import com.ssu.bilda.data.remote.response.SignUpResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface UserService {

    //회원가입
    @POST("/api/v1/users/signup")
    fun signUp(@Body request: SignUpRequest): Call<BaseResponse<SignUpResponse>>

    //로그인 요청
    @POST("/api/v1/users/signin")
    fun signIn(@Body request: SignInRequest): Call<BaseResponse<AuthorizedResponse>>

    //비밀번호 변경 요청
    @PUT("/api/v1/users/password")
    fun changePassword(@Body request: ChangePasswordRequest): Call<BaseResponse<ChangePasswordResponse>>

    //닉네임 변경 요청
    @PUT("/api/v1/users/nickname")
    fun changeNickname(@Body request: ChangeNicknameRequest): Call<BaseResponse<ChangeNicknameResponse>>

}