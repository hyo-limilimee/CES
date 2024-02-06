package com.ssu.bilda.data.common

import com.ssu.bilda.data.enums.RecruitmentStatus

data class TeamsOfSubject(
    val teamId: Long,
    val teamTitle: String,
    val subjectName: String,
    val recruitmentStatus: RecruitmentStatus,
    val memberNum: Int,
    val maxMemberNum: Int
)

