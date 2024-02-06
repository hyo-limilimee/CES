package com.ssu.bilda.data.remote.request

data class TeamJoinRequest(
    val userId: Long,
    val userName: String,
    val department: String
)
