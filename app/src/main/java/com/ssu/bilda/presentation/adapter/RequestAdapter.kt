package com.ssu.bilda.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.content.Context
import android.widget.Toast
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.ssu.bilda.R
import com.ssu.bilda.data.common.UserData
import com.ssu.bilda.data.remote.RetrofitImpl
import com.ssu.bilda.data.remote.response.BaseResponse
import com.ssu.bilda.data.service.TeamAcceptRequestService
import com.ssu.bilda.data.service.TeamRejectRequestService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RequestAdapter(private val teamId: Long, private val context: Context) : RecyclerView.Adapter<RequestAdapter.RequestViewHolder>() {

    private var requestList: List<UserData> = emptyList()

    fun setRequestList(list: List<UserData>) {
        requestList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rcv_request, parent, false)
        return RequestViewHolder(view)
    }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {
        val userData = requestList[position]

        // TextView에 사용자 이름 설정
        holder.requestNameTextView.text = userData.userName

        // Reject 버튼 클릭 처리
        holder.rejectButton.setOnClickListener {
            val userId = userData.userId
            // teamId와 userId를 사용하여 rejectJoinRequest 호출
            rejectJoinRequest(teamId, userId)
        }

        // Accept 버튼 클릭 처리
        holder.acceptButton.setOnClickListener {
            val userId = userData.userId
            acceptJoinRequest(teamId, userId)
        }
    }

    override fun getItemCount(): Int {
        return requestList.size
    }

    class RequestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val requestNameTextView: TextView = itemView.findViewById(R.id.tv_request_name)
        val rejectButton: FrameLayout = itemView.findViewById(R.id.fl_reject_btn)
        val acceptButton: FrameLayout = itemView.findViewById(R.id.fl_accept_btn)
    }

    private fun rejectJoinRequest(teamId: Long, userId: Long) {
        // Retrofit을 사용하여 서버에 rejectJoinRequest API를 호출합니다.
        val rejectCall: Call<BaseResponse<Any>> =
            RetrofitImpl.authenticatedRetrofit.create(TeamRejectRequestService::class.java)
                .rejectJoinRequest(teamId, userId)

        rejectCall.enqueue(object : Callback<BaseResponse<Any>> {
            override fun onResponse(
                call: Call<BaseResponse<Any>>,
                response: Response<BaseResponse<Any>>
            ) {
                if (response.isSuccessful) {
                    val rejectResponse = response.body()
                    if (rejectResponse != null) {
                        // 응답을 처리합니다.
                        Log.d("RejectJoinRequest", "success: ${rejectResponse.success}")
                        Log.d("RejectJoinRequest", "code: ${rejectResponse.code}")
                        Log.d("RejectJoinRequest", "message: ${rejectResponse.message}")
                        
                        showToast("참가 요청을 거절했습니다.")
                    } else {
                        Log.e("RejectJoinRequest", "응답 바디가 null입니다.")
                    }
                } else {
                    Log.e("RejectJoinRequest", "에러: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<BaseResponse<Any>>, t: Throwable) {
                Log.e("RejectJoinRequest", "에러: ${t.message}")
            }
        })
    }

    private fun acceptJoinRequest(teamId: Long, userId: Long) {
        // acceptJoinRequest API를 호출하는 Retrofit 서비스 인터페이스 생성
        val acceptJoinRequestService = RetrofitImpl.authenticatedRetrofit.create(
            TeamAcceptRequestService::class.java)

        // 서버에 acceptJoinRequest 요청 보내기
        val call: Call<BaseResponse<Any>> = acceptJoinRequestService.acceptJoinRequest(teamId, userId)

        // 요청을 대기열에 추가
        call.enqueue(object : Callback<BaseResponse<Any>> {
            override fun onResponse(
                call: Call<BaseResponse<Any>>,
                response: Response<BaseResponse<Any>>
            ) {
                if (response.isSuccessful) {
                    val acceptResponse = response.body()
                    if (acceptResponse != null) {
                        // 응답을 처리합니다.
                        Log.d("AcceptJoinRequest", "success: ${acceptResponse.success}")
                        Log.d("AcceptJoinRequest", "code: ${acceptResponse.code}")
                        Log.d("AcceptJoinRequest", "message: ${acceptResponse.message}")
                        // result에 따른 추가 처리
                        showToast("Join request accepted")
                    } else {
                        Log.e("AcceptJoinRequest", "응답 바디가 null입니다.")
                    }
                } else {
                    Log.e("AcceptJoinRequest", "에러: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<BaseResponse<Any>>, t: Throwable) {
                Log.e("참가요청을 수락했습니다.", "에러: ${t.message}")
            }
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
