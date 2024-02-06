package com.ssu.bilda.data.remote.response

data class GptCompletionChatResponse(
    val id: String,
    val `object`: String,
    val created: Long,
    val model: String,
    val messages: List<Message>,
    val usage: Usage
)

data class Message(
    val role: String,
    val message: String
)

data class Usage(
    val promptTokens: Long,
    val completionTokens: Long,
    val totalTokens: Long
)
