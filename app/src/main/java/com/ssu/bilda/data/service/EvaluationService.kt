package com.ssu.bilda.data.service

import com.ssu.bilda.data.common.User
import com.ssu.bilda.data.remote.request.EvaluationRequest
import com.ssu.bilda.data.remote.response.BaseResponse
import com.ssu.bilda.data.remote.response.SubjectResponse
import com.ssu.bilda.data.remote.response.TeamResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface EvaluationService {

    // 유저가 수강하는 과목 불러오기
    @GET("/api/v1/subject")
    suspend fun getUserSubjects(): SubjectResponse

    // 유저가 속한 팀 불러오기
    @GET("/api/v1/teams/user")
    suspend fun getUserTeams(): Response<List<EvaluationTeamResponse>>

    // 평가 요청 보내기
    @POST("/api/v1/evaluation") // replace with your actual endpoint path
    fun sendEvaluationRequest(@Body request: EvaluationRequest): Call<BaseResponse<User>>
}