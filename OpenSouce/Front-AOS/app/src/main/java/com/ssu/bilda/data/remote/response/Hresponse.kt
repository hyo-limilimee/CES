package com.ssu.bilda.data.remote.response

data class Hresponse<T>(
    val statusCode: Int,
    val message: String,
    val data: T? = null,
)
