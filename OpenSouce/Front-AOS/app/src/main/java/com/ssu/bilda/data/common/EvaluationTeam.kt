package com.ssu.bilda.data.common

import com.google.gson.annotations.SerializedName

data class EvaluationTeam(
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
    val members: List<EvaluationTeamMember>
)
