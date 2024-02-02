package com.ssu.bilda.data.service

import com.ssu.bilda.data.remote.request.GptCompletionChatRequest
import com.ssu.bilda.data.remote.response.GptCompletionChatResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ChatService {
    @POST("/api/v1/completion/chat")
    fun sendChatMessage(@Body request: GptCompletionChatRequest): Call<GptCompletionChatResponse>
}