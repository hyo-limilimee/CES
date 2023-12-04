package com.ssu.bilda.data.common

data class TeamDetail(
    val teamId: Long,
    val leaderId: Long,
    val teamTitle: String,
    val subjectTitle: String,
    val leaderName: String,
    val recruitmentStatus: String,
    val completeStatus: String,
    val buildStartDate: String?, // You might want to change the type based on your actual data
    val members: List<TeamMemberWithNickName>
)
