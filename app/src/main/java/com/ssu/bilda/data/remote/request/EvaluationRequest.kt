package com.ssu.bilda.data.remote.request

import Scores

data class EvaluationRequest(
    val evaluatedUserId: Int,
    val teamId: Int,
    val scores: Scores
)