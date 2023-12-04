package com.ssu.bilda.data.common

data class TeamInfo(
    val success: Boolean,
    val code: Int,
    val message: String,
    val result: TeamDetail
)
