package com.ssu.bilda.data.remote.response

data class RejectJoinRequestResponse(
    val success: Boolean,
    val code: Int,
    val message: String,
    val result: Any // result의 구체적인 형식에 따라 수정
)
