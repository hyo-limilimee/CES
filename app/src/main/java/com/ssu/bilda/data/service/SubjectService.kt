package com.ssu.bilda.data.service

import com.ssu.bilda.data.common.Subject
import com.ssu.bilda.data.common.SubjectWithTeamStatus
import com.ssu.bilda.data.common.User
import com.ssu.bilda.data.remote.response.BaseResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.Call

interface SubjectService {

    //유저가 속해있는 과목 정보 가져오기
    @GET("/api/v1/subject/{userId}")
    fun getUserSubjects(@Path("userId") userId: Long): Call<BaseResponse<List<SubjectWithTeamStatus>>>


    //유저가 속한 학과에 개설된 과목 정보 가져오기
    @GET("/api/v1/subject/department/{userId}")
    fun getUserDepartmentSubjects(@Path("userId") userId: Long): Call<BaseResponse<List<Subject>>>


    // 유저가 듣고 있는 과목 추가하기
    @POST(" /api/v1/subject/{userId}/add/{subjectCode}")
    fun addUserSubject(
        @Path("userId") userId: Long,
        @Path("subjectCode") subjectCode: Long
    ): Call<BaseResponse<User>>

}