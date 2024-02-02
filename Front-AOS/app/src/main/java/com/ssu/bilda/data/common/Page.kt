package com.ssu.bilda.data.common

data class Page(
    val id: Long,
    val user: User,
    val userInfoMessage: String,
    val temperature: Int,
    val reMatchingHopeRate: Double
)
