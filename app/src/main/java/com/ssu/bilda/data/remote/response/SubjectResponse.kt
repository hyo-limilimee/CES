package com.ssu.bilda.data.remote.response

import com.google.gson.annotations.SerializedName
import com.ssu.bilda.data.common.Subject

data class SubjectResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: List<Subject>?
)
