package com.ssu.bilda.data.remote.response

import com.ssu.bilda.data.common.SubjectWithTeamStatus

data class SubjectResponse(
    val success: Boolean,
    val code: Int,
    val message: String,
    val result: List<SubjectWithTeamStatus>
)

