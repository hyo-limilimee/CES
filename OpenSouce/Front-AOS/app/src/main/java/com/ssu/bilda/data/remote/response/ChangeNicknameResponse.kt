package com.ssu.bilda.data.remote.response

import com.google.gson.annotations.SerializedName

data class ChangeNicknameResponse(
    @SerializedName("nickname")
    val nickname: String
)
