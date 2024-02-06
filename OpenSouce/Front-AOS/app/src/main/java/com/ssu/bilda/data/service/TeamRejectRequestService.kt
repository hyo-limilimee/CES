package com.ssu.bilda.data.service

import com.ssu.bilda.data.remote.response.BaseResponse
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Path

interface TeamRejectRequestService {

    // 팀 조인 요청 거절하기
    @POST("/api/v1/teams/{teamId}/reject/{pendingUserId}")
    fun rejectJoinRequest(
        @Path("teamId") teamId: Long,
        @Path("pendingUserId") pendingUserId: Long
    ): Call<BaseResponse<Any>>
}