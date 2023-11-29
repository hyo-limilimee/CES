package com.ssu.bilda.data.remote.request

import java.util.Date

data class CreateTeamRequest(
    val subjectId: Long,
    val teamTitle: String,
    val recruitmentEndDate: Date,
    val maxMember: Int,
    val teamInfoMessage: String
)
