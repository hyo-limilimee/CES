package com.ssu.bilda.data.common

data class TeamDetail(
    val teamId: Long,
    val leaderId: Long,
    val teamTitle: String,
    val subjectTitle: String,
    val leaderName: String,
    val recruitmentStatus: String,
    val completeStatus: String,
    val buildStartDate: String?,
    val teamInfoMessage : String?,
    val maxNumber : Int,
    val members: List<TeamMemberWithNickName>
)
