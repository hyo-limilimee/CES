package com.ssu.bilda.data.service

import com.ssu.bilda.data.remote.response.BaseResponse
import com.ssu.bilda.data.remote.response.MyPageResponse
import retrofit2.http.GET

interface MypageService {

    @GET("/api/v1/page")
    suspend fun getMyPage(): BaseResponse<MyPageResponse>
}