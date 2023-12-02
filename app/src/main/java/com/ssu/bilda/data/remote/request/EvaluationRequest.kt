package com.ssu.bilda.data.remote.request

import Scores

data class EvaluationRequest(
    val evaluatedUserId: Long,
    val teamId: Long,
    val scores: Scores
)

