package com.ssu.bilda.data.service

import com.ssu.bilda.data.common.SubjectWithTeamStatus
import com.ssu.bilda.data.remote.response.BaseResponse
import com.ssu.bilda.data.remote.response.SubjectResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface EvaluationService {
    //유저가 속해있는 과목 정보 가져오기
    @GET("/api/v1/subject")
    fun getUserSubjects(): Response<SubjectResponse>

}