package com.ssu.bilda.data.remote.response

data class MyPageResponse(
    val userId: Int,
    val userName: String,
    val scoreItems: List<ScoreItem>
)

data class ScoreItem(
    val evaluationItemName: String,
    val averageScore: Int,
    val highScoreCount: Int
)