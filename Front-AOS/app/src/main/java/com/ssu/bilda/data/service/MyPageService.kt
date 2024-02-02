package com.ssu.bilda.data.service

import com.ssu.bilda.data.remote.response.MyPageReferResponse
import com.ssu.bilda.data.remote.response.MyPageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MyPageService {
    @GET("/api/v1/page")
    suspend fun getMyPage(): Response<MyPageResponse>

    @GET("/api/v1/page/{userId}")
    suspend fun getMyPageRefer(@Path("userId") userId: Long): Response<MyPageReferResponse>
}