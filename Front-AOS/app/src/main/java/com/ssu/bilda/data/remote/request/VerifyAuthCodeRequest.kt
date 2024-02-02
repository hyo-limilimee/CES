package com.ssu.bilda.data.remote.request

import com.google.gson.annotations.SerializedName

data class VerifyAuthCodeRequest(
    @SerializedName("email")
    val email: String,

    @SerializedName("authCode")
    val authCode: String
)
