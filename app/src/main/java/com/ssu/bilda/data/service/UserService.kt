package com.ssu.bilda.data.service

import com.ssu.bilda.data.remote.request.SignUpRequest
import com.ssu.bilda.data.remote.response.SignUpResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {

    //회원가입
    @POST("/api/v1/users/signup")
    fun signUp(@Body request: SignUpRequest): Call<SignUpResponse>

}