package com.ssu.bilda.data.service

import com.ssu.bilda.data.remote.response.MyPageResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface MyPageService {
    @GET("/api/v1/page")
    suspend fun getMyPage(): Response<MyPageResponse>
}