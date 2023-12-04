package com.ssu.bilda.presentation.teambuild

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.ssu.bilda.R
import com.ssu.bilda.data.common.TeamDetail
import com.ssu.bilda.data.remote.RetrofitImpl
import com.ssu.bilda.data.remote.response.TeamInfoResponse
import com.ssu.bilda.data.service.TeamService
import com.ssu.bilda.presentation.adapter.TeammateProfileAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TeamBuildPostViewGeneral : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_team_build_post_view_general, container, false)

        // Make API call when the fragment is created
        getTeamInfo(view)

        return view
    }

    private fun getTeamInfo(view: View) {
        val retrofit = RetrofitImpl.authenticatedRetrofit
        val teamService = retrofit.create(TeamService::class.java)

        val teamId = 1L // Replace with the desired teamId
        val call = teamService.getTeamInfo(teamId)
        call.enqueue(object : Callback<TeamInfoResponse> {
            override fun onResponse(
                call: Call<TeamInfoResponse>,
                response: Response<TeamInfoResponse>
            ) {
                if (response.isSuccessful) {
                    val teamInfoResponse = response.body()
                    if (teamInfoResponse != null) {
                        val teamDetail: TeamDetail = teamInfoResponse.result

                        // 텍스트 뷰 세팅
                        val subjectNameTextView: TextView? = view.findViewById(R.id.tv_subject_name)
                        val teamBuildTitleTextView: TextView? = view.findViewById(R.id.et_teambuild_title)

                        subjectNameTextView?.text = teamDetail.subjectTitle
                        teamBuildTitleTextView?.text = teamDetail.teamTitle

                        // RecyclerView 초기화 및 어댑터 설정
                        val teammateRecyclerView: RecyclerView = view.findViewById(R.id.rv_teammate_profile_list)
                        val teammateAdapter = TeammateProfileAdapter(teamDetail.members)
                        teammateRecyclerView.adapter = teammateAdapter

                        Log.d("TeamDetail", teamDetail.toString())
                    }
                } else {
                    // 처리하지 못한 응답에 대한 처리
                    Log.e(
                        "API 오류",
                        "코드: ${response.code()}, 메시지: ${response.message()}"
                    )
                }
            }

            override fun onFailure(call: Call<TeamInfoResponse>, t: Throwable) {
                // Handle failure
                Log.e("API Error", "Failed to make API call: ${t.message}")
            }
        })
    }
}
