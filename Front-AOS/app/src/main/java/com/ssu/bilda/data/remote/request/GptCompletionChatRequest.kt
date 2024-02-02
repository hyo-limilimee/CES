package com.ssu.bilda.data.remote.request

data class GptCompletionChatRequest(
    val model: String,
    val role: String,
    val message: String,
    val maxTokens: Int
)