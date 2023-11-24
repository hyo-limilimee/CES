package com.ssu.bilda.data.remote.request

import com.google.gson.annotations.SerializedName
import com.ssu.bilda.data.enums.Department

class SignUpRequest (
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("studentId")
    val studentId: String,
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("department")
    val department: Department,
)