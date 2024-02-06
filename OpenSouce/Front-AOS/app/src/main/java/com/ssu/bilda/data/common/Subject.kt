package com.ssu.bilda.data.common

import com.ssu.bilda.data.enums.Department

data class Subject(
    val subjectCode: Long,
    val title: String,
    val departments: List<String>,
    val professor: String,
    val section: String,
    val hasTeam: Boolean
)

