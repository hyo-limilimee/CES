package com.ssu.bilda.data.remote.request

import com.google.gson.annotations.SerializedName

data class VerifyEmailRequest(
    @SerializedName("email")
    val email: String,
    )
