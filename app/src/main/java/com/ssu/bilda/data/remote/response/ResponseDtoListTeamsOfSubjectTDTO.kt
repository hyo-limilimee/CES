package com.ssu.bilda.data.remote.response

import com.google.gson.annotations.SerializedName

//과목별팀
data class ResponseDtoListTeamsOfSubjectDTO(
    val success: Boolean,
    val code: Int,
    val message: String,
    val result: List<TeamsOfSubjectDTO>
)

data class TeamsOfSubjectDTO(
    @SerializedName("teamId")
    val teamId: Long,
    val teamTitle: String,
    val subjectName: String,
    val recruitmentStatus: String,
    val memberNum: Int,
    val maxMemberNum: Int
)

