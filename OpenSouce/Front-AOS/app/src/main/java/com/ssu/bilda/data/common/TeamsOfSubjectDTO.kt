package com.ssu.bilda.data.common

data class TeamsOfSubjectDTO(
    val teamId: Long,
    val teamTitle: String,
    val subjectName: String,
    val recruitmentStatus: String,
    val memberNum: Int,
    val maxMemberNum: Int
)