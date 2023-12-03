package com.ssu.bilda.data.remote.response

import com.ssu.bilda.data.common.Subject

data class DepSubjectResponse(
    val success: Boolean,
    val code: Int,
    val message: String,
    val result: List<Subject>
)
