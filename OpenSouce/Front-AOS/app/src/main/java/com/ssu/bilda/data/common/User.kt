package com.ssu.bilda.data.common

import com.ssu.bilda.data.enums.Department
import com.ssu.bilda.data.enums.Role

data class User(
    val id: Long,
    val email: String,
    val password: String,
    val name: String,
    val nickname: String,
    val studentId: String,
    val department: Department,
    val role: Role,
    val accessToken: String,
    val refreshToken: String,
    val myPage: Page
)




