package com.ssu.bilda.data.remote.response

import com.google.gson.annotations.SerializedName

data class ChangePasswordResponse(
    @SerializedName("password")
    val password: String
)
