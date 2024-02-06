package com.ssu.bilda.data.remote.response

import com.ssu.bilda.data.common.UserData

data class JoinRequestResponse(
    val statusCode: Int,
    val message: String,
    val data: List<UserData>
)
