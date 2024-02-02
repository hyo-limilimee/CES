package com.ssu.bilda.data.common

import com.google.gson.annotations.SerializedName

data class EvaluationTeamMember(

    @SerializedName("userId")
    val userId: Int,

    @SerializedName("name")
    val name: String
)

