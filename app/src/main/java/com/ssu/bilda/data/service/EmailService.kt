package com.ssu.bilda.data.service

import com.ssu.bilda.data.remote.request.VerifyAuthCodeRequest
import com.ssu.bilda.data.remote.request.VerifyEmailRequest
import com.ssu.bilda.data.remote.response.BaseResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface EmailService {

    // 이메일 전송
    @POST("/api/v1/email/verify")
    fun sendEmail(@Body request: VerifyEmailRequest): Call<BaseResponse<Void>>

     // 이메일 인증
    @POST("/api/v1/email/verify/code")
    fun certifyEmail(@Body request: VerifyAuthCodeRequest ): Call<BaseResponse<Void>>
}
