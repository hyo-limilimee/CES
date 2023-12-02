package com.ssu.bilda.data.service

import retrofit2.Call
import com.ssu.bilda.data.remote.response.MyPageResponse
import retrofit2.http.GET

interface MyPageService {
    @GET("/api/v1/page")
    fun getMyPage(): Call<MyPageResponse>
}