package com.ssu.bilda.data.remote.request

import com.google.gson.annotations.SerializedName

data class TeamCreateRequest(
    @SerializedName("subjectId")
    val subjectId: Int,
    @SerializedName("teamTitle")
    val teamTitle: String,
    @SerializedName("recruitmentEndDate")
    val recruitmentEndDate: String,
    @SerializedName("maxMember")
    val maxMember: Int,
    @SerializedName("teamInfoMessage")
    val teamInfoMessage: String
)

