package com.ssu.bilda.data.remote.response

import com.google.gson.annotations.SerializedName

data class AuthorizedResponse(
    @SerializedName("accessToken")
    val accessToken: String,

    @SerializedName("refreshToken")
    val refreshToken: String
)
