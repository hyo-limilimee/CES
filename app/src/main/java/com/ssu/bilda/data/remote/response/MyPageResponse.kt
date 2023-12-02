package com.ssu.bilda.data.remote.response

import com.google.gson.annotations.SerializedName
import com.ssu.bilda.data.common.ScoreItem
import retrofit2.http.GET

data class MyPageResponse(
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("userName")
    val userName: String,
    @SerializedName("scoreItems")
    val scoreItems: List<ScoreItem>
)
