package com.ssu.bilda.data.remote.response

data class ResponseDtoListTeamsOfSubjectDTO(
    val success: Boolean,
    val code: Int,
    val message: String,
    val result: List<TeamsOfSubjectDTO>
)

data class TeamsOfSubjectDTO(
    val teamId: Long,
    val teamTitle: String,
    val subjectName: String,
    val recruitmentStatus: String,
    val memberNum: Int,
    val maxMemberNum: Int
)

