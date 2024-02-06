package com.ssu.bilda.data.remote.response

import com.google.gson.annotations.SerializedName

data class EvaluationStatusResponse(
    @SerializedName("userId") val userId: Long,
    @SerializedName("memberName") val memberName: String,
    @SerializedName("hasEvaluated") val hasEvaluated: Boolean
)
