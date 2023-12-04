package com.ssu.bilda.data.remote.response

import com.ssu.bilda.data.common.ScoreItem

data class MyPageReferResponse(
    val userId: Long,
    val userName: String,
    val scoreItems: List<ScoreItem>
)

