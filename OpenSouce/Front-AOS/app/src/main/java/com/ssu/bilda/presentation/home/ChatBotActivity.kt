package com.ssu.bilda.presentation.home

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.ssu.bilda.R
import com.ssu.bilda.data.remote.RetrofitImpl
import com.ssu.bilda.data.remote.request.GptCompletionChatRequest
import com.ssu.bilda.data.remote.response.GptCompletionChatResponse
import com.ssu.bilda.databinding.ActivityChatBotBinding
import com.ssu.bilda.data.service.ChatService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatBotActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBotBinding
    private lateinit var chatService: ChatService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBotBinding.inflate(layoutInflater)
        setContentView(binding.root)

        chatService = RetrofitImpl.authenticatedRetrofit.create(ChatService::class.java) // 토큰이 있는 Retrofit 사용

                // 버튼 클릭으로 되돌아가기
            binding.btnChatBack.setOnClickListener {
                finish()
            }

        // 버튼이 담긴 영역 클릭으로 되돌아가기
        binding.flChatBack.setOnClickListener {
            finish()
        }

        binding.btnSend.setOnClickListener {
            val messageToSend = binding.tvQuestion.text.toString()
            if (messageToSend.isNotBlank()) {
                val request = GptCompletionChatRequest(
                    model = "gpt-3.5-turbo", // 모델 값 설정
                    role = "user",
                    message = messageToSend,
                    maxTokens = 1000 // 최대 토큰 수 설정(고정값)
                )

                // Retrofit을 사용하여 요청 보내기
                chatService.sendChatMessage(request).enqueue(object : Callback<GptCompletionChatResponse> {
                    override fun onResponse(
                        call: Call<GptCompletionChatResponse>,
                        response: Response<GptCompletionChatResponse>
                    ) {
                        if (response.isSuccessful) {
                            val chatResponse = response.body()
                            chatResponse?.let {
                                // 서버로부터 받은 메시지 표시
                                displayResponse(it.messages.firstOrNull()?.message ?: "응답없음")
                            }
                        } else {
                            // 실패했을 때의 처리
                            displayResponse("응답실패")
                        }
                    }

                    override fun onFailure(call: Call<GptCompletionChatResponse>, t: Throwable) {
                        // 통신 실패 시 처리
                        displayResponse("Error: ${t.message}")
                    }
                })
            }
        }
    }

    private fun displayResponse(response: String) {
        runOnUiThread {
            binding.etAnswer.setText(response)
        }
    }
}
