package com.ssu.bilda.data.remote.response

import com.ssu.bilda.data.common.ScoreItem

data class MyPageResponse(
    val userId: Int,
    val userName: String,
    val scoreItems: List<ScoreItem>
)
