package com.ssu.bilda.data.common

import com.ssu.bilda.data.enums.Department

data class PendingUser(
    val userId: Long,
    val userName: String,
    val department: Department,
)
