package com.ssu.bilda.data.common

import com.google.gson.annotations.SerializedName

data class Member(
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("name")
    val name: String
)
