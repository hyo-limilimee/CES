package com.ssu.bilda.data.service

import com.ssu.bilda.data.common.PendingUser
import com.ssu.bilda.data.remote.request.CreateTeamRequest
import com.ssu.bilda.data.remote.request.TeamCreateRequest
import com.ssu.bilda.data.remote.response.BaseResponse
import com.ssu.bilda.data.remote.response.ResponseDtoListTeamsOfSubjectDTO
import com.ssu.bilda.data.remote.response.TeamCreateResponse
import com.ssu.bilda.data.remote.response.TeamInfoResponse
import com.ssu.bilda.data.remote.response.TeamResponse
import com.ssu.bilda.data.remote.response.TeamResponseDTO
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TeamService {


    // 팀의 정보 가져오기
    @GET("/api/v1/teams/{teamId}")
    fun getTeamInfo(@Path("teamId") teamId: Long): Call<TeamInfoResponse>

    // 팀 조인 요청 확인하기
    @GET("/api/v1/teams/{teamId}/recruit")
    fun getPendingUsers(@Path("teamId") teamId: Long): Call<BaseResponse<List<PendingUser>>>

    //  User가 속해있는 팀들의 정보를 가져오기
    @GET(" /api/v1/teams/user/{userId}")
    fun getUserTeams(@Path("userId") userId: Long): Call<BaseResponse<List<TeamResponse>>>

    //  과목에 해당하는 팀들의 정보 가져오기
    @GET("/api/v1/teams/subject/{subjectId}")
    fun getTeamsBySubject(@Path("subjectId") subjectId: Long): Call<ResponseDtoListTeamsOfSubjectDTO>


    // 팀 조인 요청 거절하기
    @POST("/api/v1/teams/{teamId}/reject/{pendingUserId}")
    fun rejectJoinRequest(
        @Path("teamId") teamId: Long,
        @Path("pendingUserId") pendingUserId: Long
    ): Call<BaseResponse<Void>>


    //  팀 조인 요청 수락하기
    @POST("/api/v1/teams/{teamId}/approve/{pendingUserId}")
    fun approveJoinRequest(
        @Path("teamId") teamId: Long,
        @Path("pendingUserId") pendingUserId: Long
    ): Call<BaseResponse<Void>>


    //   팀 생성하기
    @POST("/api/v1/teams/create")
    suspend fun createTeam(@Body request: TeamCreateRequest): Response<TeamCreateResponse>


    //  팀플 종료하기
    @POST("/api/v1/teams/complete/{teamId}")
    fun completeTeam(
        @Path("teamId") teamId: Long
    ): Call<BaseResponse<Void>>

}