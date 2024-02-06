package com.ssu.bilda.data.remote.response

import com.google.gson.annotations.SerializedName
import com.ssu.bilda.data.common.Member


data class TeamCreateResponse(
    @SerializedName("teamId")
    val teamId: Int,
    @SerializedName("leaderId")
    val leaderId: Int,
    @SerializedName("teamTitle")
    val teamTitle: String,
    @SerializedName("subjectTitle")
    val subjectTitle: String,
    @SerializedName("leaderName")
    val leaderName: String,
    @SerializedName("recruitmentStatus")
    val recruitmentStatus: String,
    @SerializedName("completeStatus")
    val completeStatus: String,
    @SerializedName("buildStartDate")
    val buildStartDate: String,
    @SerializedName("members")
    val members: List<Member>
)
