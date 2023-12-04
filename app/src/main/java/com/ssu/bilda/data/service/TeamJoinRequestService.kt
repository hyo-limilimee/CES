package com.ssu.bilda.data.service

import com.ssu.bilda.data.remote.response.BaseResponse
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Path

interface TeamJoinRequestService {

    // 팀에 조인 요청하기
    @POST("/api/v1/teams/{teamId}/join")
    fun joinTeam(@Path("teamId") teamId: Long): Call<BaseResponse<Any>>
}