package com.ssu.bilda.data.common

import com.ssu.bilda.data.enums.CompleteStatus
import com.ssu.bilda.data.enums.RecruitmentStatus
import java.util.Date

data class Team(
    val teamId: Long,
    val leader: User,
    val users: List<User>,
    val subject: Subject,
    val teamTitle: String,
    val recruitmentEndDate: Date,
    val maxMemberNum: Int,
    val recruitmentStatus: RecruitmentStatus,
    val completeStatus: CompleteStatus,
    val teamInfoMessage: String,
    val buildStartDate: Date,
    val pendingUsers: User
)
