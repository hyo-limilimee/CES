package com.ssu.bilda.presentation.teambuild

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.ssu.bilda.R
import com.ssu.bilda.data.common.TeamDetail
import com.ssu.bilda.data.remote.RetrofitImpl
import com.ssu.bilda.data.remote.response.BaseResponse
import com.ssu.bilda.data.remote.response.TeamInfoResponse
import com.ssu.bilda.data.service.TeamJoinRequestService
import com.ssu.bilda.data.service.TeamService
import com.ssu.bilda.presentation.adapter.TeammateProfileAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TeamBuildPostViewGeneral : Fragment() {


    private lateinit var teamService: TeamService
    private var currentTeamId: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 레이아웃을 인플레이트합니다.
        val view = inflater.inflate(R.layout.fragment_team_build_post_view_general, container, false)

        // 팀 정보를 가져옵니다.
        getTeamInfo(view)

        // 가입 버튼에 대한 클릭 리스너를 설정합니다.
        val joinButton: Button = view.findViewById(R.id.tv_teambuild_require_btn)
        joinButton.setOnClickListener {
            sendTeamJoinRequest()
        }

        return view
    }

    private fun getTeamInfo(view: View) {
        val retrofit = RetrofitImpl.authenticatedRetrofit
        teamService = retrofit.create(TeamService::class.java)

        // 팀 ID를 가져옵니다.
        val arguments = arguments
        if (arguments != null && arguments.containsKey("teamId")) {
            currentTeamId = arguments.getLong("teamId")
            Log.d("TeamBuildPostViewGeneral", "팀 아이디: $currentTeamId")
        }

        // API 호출을 위한 서비스 메서드를 호출합니다.
        val call = teamService.getTeamInfo(currentTeamId)
        call.enqueue(object : Callback<TeamInfoResponse> {
            override fun onResponse(
                call: Call<TeamInfoResponse>,
                response: Response<TeamInfoResponse>
            ) {
                if (response.isSuccessful) {
                    val teamInfoResponse = response.body()
                    if (teamInfoResponse != null) {
                        val teamDetail: TeamDetail = teamInfoResponse.result

                        // TextView 설정
                        val subjectNameTextView: TextView? = view.findViewById(R.id.tv_subject_name)
                        val teamBuildTitleTextView: TextView? =
                            view.findViewById(R.id.et_teambuild_title)
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

                        // RecyclerView 아이템 수 가져오기
                        val recyclerViewItemCount = teammateAdapter.itemCount

                        // 리스폰스에서 받은 maxCount 값
                        val maxCount = teamDetail.maxNumber

                        // 텍스트뷰에 설정할 문자열 생성
                        val displayText = "$recyclerViewItemCount/$maxCount"

                        // 텍스트뷰에 문자열 설정
                        val teammateNumTextView: TextView = view.findViewById(R.id.tv_teammate_num)
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


    private fun sendTeamJoinRequest() {
        val retrofit = RetrofitImpl.authenticatedRetrofit
        val teamJoinRequestService = retrofit.create(TeamJoinRequestService::class.java)

        // 팀 가입 요청을 보냅니다.
        val call = teamJoinRequestService.joinTeam(currentTeamId)

        call.enqueue(object : Callback<BaseResponse<Any>> {
            override fun onResponse(
                call: Call<BaseResponse<Any>>,
                response: Response<BaseResponse<Any>>
            ) {
                if (response.isSuccessful) {
                    // 팀 가입 요청 성공
                    Log.d("TeamJoinRequest", "팀 가입 요청 성공")
                    showToast("팀 가입 요청이 완료되었습니다.")
                    // TODO: 성공 시 처리, 예를 들어 사용자에게 메시지 표시
                } else {
                    // 팀 가입 요청 실패
                    Log.e("TeamJoinRequest", "오류: ${response.code()}, ${response.message()}")
                    showToast("팀 가입 요청에 실패했습니다.")
                    // TODO: 오류 시 처리, 예를 들어 사용자에게 오류 메시지 표시
                }
            }

            override fun onFailure(call: Call<BaseResponse<Any>>, t: Throwable) {
                // 실패 처리
                Log.e("TeamJoinRequest", "실패: ${t.message}")
                showToast("팀 가입 요청에 실패했습니다.")
                // TODO: 실패 시 처리, 예를 들어 사용자에게 오류 메시지 표시
            }
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}