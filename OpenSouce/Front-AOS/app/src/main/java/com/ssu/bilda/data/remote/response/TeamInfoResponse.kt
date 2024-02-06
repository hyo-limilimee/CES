package com.ssu.bilda.data.remote.response

import com.ssu.bilda.data.common.TeamDetail

data class TeamInfoResponse(
    val success: Boolean,
    val code: Int,
    val message: String,
    val result: TeamDetail
)
