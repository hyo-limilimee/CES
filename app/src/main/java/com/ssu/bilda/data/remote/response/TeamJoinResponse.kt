package com.ssu.bilda.data.remote.response

import com.ssu.bilda.data.remote.request.TeamJoinRequest

data class TeamJoinResponse(
    val statusCode: Int,
    val message: String,
    val data: List<TeamJoinRequest>
)
