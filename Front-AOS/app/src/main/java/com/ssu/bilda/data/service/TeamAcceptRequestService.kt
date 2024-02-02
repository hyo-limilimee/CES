package com.ssu.bilda.data.service

import com.ssu.bilda.data.remote.response.BaseResponse
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Path

interface TeamAcceptRequestService {
    @POST("/api/v1/teams/{teamId}/approve/{pendingUserId}")
    fun acceptJoinRequest(
        @Path("teamId") teamId: Long,
        @Path("pendingUserId") pendingUserId: Long
    ): Call<BaseResponse<Any>>
}