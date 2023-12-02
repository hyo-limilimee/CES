package com.ssu.bilda.data.remote.response

import com.google.gson.annotations.SerializedName
import com.ssu.bilda.data.enums.Department

data class SignInResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("studentId")
    val studentId: String,
    @SerializedName("department")
    val department: Department,
)
