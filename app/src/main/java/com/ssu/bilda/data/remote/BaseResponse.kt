package com.ssu.bilda.data.remote

data class BaseResponse<T>(
    val success: Boolean,
    val code: Int,
    val message: String,
    val result: T? = null,
)
