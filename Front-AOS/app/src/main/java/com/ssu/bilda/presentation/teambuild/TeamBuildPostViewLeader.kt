package com.ssu.bilda.presentation.teambuild

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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

 class TeamBuildPostViewLeader : Fragment() {

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val view =
                inflater.inflate(R.layout.fragment_team_build_post_view_leader, container, false)

            // 인수에서 teamId를 가져옴
            val teamId = arguments?.getLong("teamId", 0L) ?: 0L

            // 로그 추가
            Log.d("TeamBuildPostViewLeader", "팀 아이디: $teamId")


            // 팀 정보 및 팀 아이디를 가져옵니다.
            getTeamInfo(view, teamId)

            val finishButton = view.findViewById<Button>(R.id.tv_teambuild_check_request_btn)

            // 버튼에 OnClickListener 설정
            finishButton.setOnClickListener {
                // 여기서 teamId를 사용할 수 있습니다.
                Log.d("TeamBuildPostViewLeader", "팀 아이디: $teamId")

                // AcceptJoinRequestFragment로 전환
                val acceptJoinRequestFragment = AcceptJoinRequestFragment.newInstance(teamId)

                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fl_content, acceptJoinRequestFragment)
                    .addToBackStack(null)
                    .commit()
            }

            return view
        }

        private fun getTeamInfo(view: View, teamId: Long) {
            val retrofit = RetrofitImpl.authenticatedRetrofit
            val teamService = retrofit.create(TeamService::class.java)

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
                            val subjectNameTextView: TextView? =
                                view.findViewById(R.id.tv_subject_name)
                            val teamBuildTitleTextView: TextView? =
                                view.findViewById(R.id.tv_teambuild_title)
                            val teamBuildMessageTextView: TextView? =
                                view.findViewById(R.id.tv_teambuild_writing_content)

                            subjectNameTextView?.text = teamDetail.subjectTitle
                            teamBuildTitleTextView?.text = teamDetail.teamTitle
                            teamBuildMessageTextView?.text = teamDetail.teamInfoMessage

                            // RecyclerView 초기화 및 어댑터 설정
                            val teammateRecyclerView: RecyclerView =
                                view.findViewById(R.id.rv_teammate_profile_list)
                            val teammateAdapter = TeammateProfileAdapter(teamDetail.members)
                            teammateRecyclerView.adapter = teammateAdapter

                            // 리싸이클러뷰 아이템 수 가져오기
                            val recyclerViewItemCount = teammateAdapter.itemCount

                            // 리스폰스에서 받은 maxCount 값
                            val maxCount = teamDetail.maxNumber // 이 부분은 실제 데이터에 따라서 변경되어야 합니다.

                            // 텍스트뷰에 설정할 문자열 생성
                            val displayText = "$recyclerViewItemCount/$maxCount"

                            // 텍스트뷰에 문자열 설정
                            val teammateNumTextView: TextView =
                                view.findViewById(R.id.tv_teammate_num)
                            teammateNumTextView.text = displayText

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
                    // 실패 처리
                    Log.e("API 오류", "API 호출 실패: ${t.message}")
                }
            })
        }
    }