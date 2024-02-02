package com.ssu.bilda.data.remote.response

import com.ssu.bilda.data.enums.CompleteStatus
import com.ssu.bilda.data.enums.RecruitmentStatus
import java.util.Date

data class TeamResponse(
    val success: Boolean,
    val code: Int,
    val message: String,
    val result: List<Team>
)

data class Team(
    val teamId: Int,
    val teamTitle: String,
    val subjectName: String,
    val recruitmentStatus: String,
    val memberNum: Int,
    val maxMemberNum: Int
)
