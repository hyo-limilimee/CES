package com.ssu.bilda.presentation.teambuild

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssu.bilda.R
import com.ssu.bilda.data.remote.RetrofitImpl
import com.ssu.bilda.data.remote.response.BaseResponse
import com.ssu.bilda.data.remote.response.JoinRequestResponse
import com.ssu.bilda.data.service.TeamAcceptRequestService
import com.ssu.bilda.data.service.TeamJoinRequestService
import com.ssu.bilda.data.service.TeamRejectRequestService
import com.ssu.bilda.presentation.adapter.RequestAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AcceptJoinRequestFragment : Fragment() {

    companion object {
        private const val ARG_TEAM_ID = "teamId"

        fun newInstance(teamId: Long): AcceptJoinRequestFragment {
            val fragment = AcceptJoinRequestFragment()
            val args = Bundle()
            args.putLong(ARG_TEAM_ID, teamId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_accept_join_request, container, false)

        val teamId: Long = arguments?.getLong(ARG_TEAM_ID, 0) ?: 0
        Log.d("AcceptJoinRequestFragment", "Received teamId: $teamId")

        // 네트워크 요청을 예시로 수행합니다.
        getJoinRequests(teamId)

        // 이 프래그먼트의 화면을 반환합니다.
        return view
    }

    private fun getJoinRequests(teamId: Long) {
        // 조인 요청을 가져오기 위한 네트워크 요청을 수행합니다.
        val call: Call<JoinRequestResponse> =
            RetrofitImpl.authenticatedRetrofit.create(TeamJoinRequestService::class.java)
                .getJoinRequests(teamId)

        // 요청을 대기열에 추가합니다.
        call.enqueue(object : Callback<JoinRequestResponse> {
            override fun onResponse(
                call: Call<JoinRequestResponse>,
                response: Response<JoinRequestResponse>
            ) {
                if (response.isSuccessful) {
                    val joinRequestResponse = response.body()
                    if (joinRequestResponse != null) {
                        // 응답을 여기서 처리합니다.
                        Log.d("JoinRequestResponse", "상태 코드: ${joinRequestResponse.statusCode}")
                        Log.d("JoinRequestResponse", "메시지: ${joinRequestResponse.message}")
                        Log.d("JoinRequestResponse", "데이터: ${joinRequestResponse.data}")

                        // 리사이클러뷰 어댑터를 생성하고 데이터를 설정합니다.
                        val recyclerView: RecyclerView? = view?.findViewById(R.id.rv_teammate_profile_list)
                        val adapter = RequestAdapter(teamId, requireContext())
                        adapter.setRequestList(joinRequestResponse.data)

                        // 리사이클러뷰에 어댑터를 설정합니다.
                        recyclerView?.adapter = adapter
                        recyclerView?.layoutManager = LinearLayoutManager(context)
                    } else {
                        Log.e("JoinRequestResponse", "응답 바디가 null입니다.")
                    }
                } else {
                    Log.e("JoinRequestResponse", "에러: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<JoinRequestResponse>, t: Throwable) {
                Log.e("JoinRequestResponse", "에러: ${t.message}")
            }
        })
    }
}

