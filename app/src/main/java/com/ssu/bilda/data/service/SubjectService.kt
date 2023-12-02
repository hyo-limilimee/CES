package com.ssu.bilda.data.service

import com.ssu.bilda.data.common.Subject
import com.ssu.bilda.data.common.SubjectWithTeamStatus
import com.ssu.bilda.data.common.UserSubject
import com.ssu.bilda.data.remote.response.BaseResponse
import com.ssu.bilda.data.remote.response.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.Call
import retrofit2.http.Body

interface SubjectService {

    //유저가 속해있는 과목 정보 가져오기
    @GET("/api/v1/subject")
    fun getUserSubjects(): Call<BaseResponse<List<SubjectWithTeamStatus>>>


    //유저가 속한 학과에 개설된 과목 정보 가져오기
    @GET("/api/v1/subject/departments")
    fun getUserDepartmentSubjects(): Call<BaseResponse<List<Subject>>>


    // 유저가 듣고 있는 과목 추가하기
    @POST("/api/v1/subject/add/{subjectCode}")
    fun addUserSubject(
        @Path("subjectCode") subjectCode: Long,
        @Body userSubject: UserSubject
    ): Call<Response<UserSubject>>

}