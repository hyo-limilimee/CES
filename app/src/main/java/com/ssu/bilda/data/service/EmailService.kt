package com.ssu.bilda.data.service

import com.ssu.bilda.data.remote.request.VerifyEmailRequest
import com.ssu.bilda.data.remote.response.BaseResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Query

interface EmailService {

    // 이메일 전송
    @GET("/api/v1/email/verify")
    fun sendEmail(
        @Body request: VerifyEmailRequest
    ): Call<BaseResponse<Void>>

    // 이메일 인증
    @GET("/api/v1/email/verify/code")
    fun certifyEmail(
        @Query("email") email: String,
        @Query("code") code: String,
    ): Call<BaseResponse<Void>>
}
