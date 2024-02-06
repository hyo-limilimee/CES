package com.ssu.bilda.data.service

import com.ssu.bilda.data.remote.response.EvaluationStatusResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface EvaluationStatusService {
    @GET("/api/v1/evaluation/status/{teamId}")
    suspend fun getEvaluationStatus(@Path("teamId") teamId: Long): List<EvaluationStatusResponse>
}