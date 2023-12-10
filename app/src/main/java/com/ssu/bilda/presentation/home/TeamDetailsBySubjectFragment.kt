package com.ssu.bilda.presentation.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssu.bilda.R
import com.ssu.bilda.data.remote.RetrofitImpl
import com.ssu.bilda.data.remote.UserSharedPreferences
import com.ssu.bilda.data.remote.response.BaseResponse
import com.ssu.bilda.data.service.EvaluationService
import com.ssu.bilda.data.service.TeamService
import com.ssu.bilda.presentation.adapter.HomeTeamMemberAdapter
import com.ssu.bilda.presentation.evaluate.TeammateEvalutionFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TeamDetailsBySubjectFragment : Fragment() {
    private var selectedTeamId: Int = 0; //팀아이디 저장
    private lateinit var teammateNameAdapter: HomeTeamMemberAdapter

    companion object {
        fun newInstance(title: String): TeamDetailsBySubjectFragment {
            val fragment = TeamDetailsBySubjectFragment()
            val args = Bundle()
            args.putString("title", title)

            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_team_details_by_subject, container, false)
        val title = arguments?.getString("title", "") ?: ""

        val tvFinishTeam: TextView = view.findViewById(R.id.tv_home_team_finish) // 종료 버튼

        tvFinishTeam.setOnClickListener {
            if (selectedTeamId != 0) { // selectedTeamId가 0이 아닌 경우에만 통신을 시도하도록 체크
                completeTeam(selectedTeamId)
            } else {
                Log.d("TeamDetailsBySubjectFragment", "팀 아이디 유효하지 않음")
            }
        }

        val recyclerView: RecyclerView = view.findViewById(R.id.rcv_home_team_members)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        teammateNameAdapter = HomeTeamMemberAdapter(requireContext(), emptyList())
        recyclerView.adapter = teammateNameAdapter

        fetchTeammateNames(title)

        val tvSubjectName: TextView = view.findViewById(R.id.tv_home_subject)
        tvSubjectName.text = title

        teammateNameAdapter.setOnItemClickListener { selectedMember ->
            val bundle = Bundle()
            bundle.putInt("selectedMemberId", selectedMember.userId)
            bundle.putString("selectedMemberName", selectedMember.name)

            val teammateEvaluationFragment = TeammateEvalutionFragment()
            teammateEvaluationFragment.arguments = bundle  // 번들을 프래그먼트에 추가
            replaceFragment(teammateEvaluationFragment, bundle)
        }
        return view
    }

    private fun fetchTeammateNames(title: String) {
        Log.d("TeamDetailsBySubjectFragment", "Fetching teammates for subject: $title") // 주제 확인 로그 추가

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val response = RetrofitImpl.authenticatedRetrofit
                    .create(EvaluationService::class.java)
                    .getUserTeams()

                Log.d("TeamDetailsBySubjectFragment", "Raw Response: ${response.raw()}")

                if (response.isSuccessful) {
                    val teamsWithMembers = response.body()?.result
                    teamsWithMembers?.let { teams ->
                        // 일치하는 팀 찾기
                        val selectedTeams = teams.filter { team ->
                            team.subjectTitle == title
                        }

                        Log.d("TeamDetailsBySubjectFragment", "Selected Teams: $selectedTeams")

                        if (selectedTeams.isNotEmpty()) {
                            // 여러 팀이 일치할 수 있으므로 첫 번째 팀을 선택
                            val selectedTeam = selectedTeams.first()

                            selectedTeamId = selectedTeam.teamId

                            // 선택된 팀의 멤버들을 리사이클러뷰에 추가
                            val teamMembers = selectedTeam.members
                            Log.d("TeamDetailsBySubjectFragment", "Selected Team: $selectedTeam")
                            Log.d("TeamDetailsBySubjectFragment", "Team Members: $teamMembers")
                            Log.d("TeamDetailsBySubjectFragment", "Team Id: $selectedTeamId")


                            teammateNameAdapter.updateData(teamMembers)

                            val responseBody = response.body()?.toString()
                            Log.d("TeamDetailsBySubjectFragment", "Response Body: $responseBody")
                        } else {
                            Log.d("TeamDetailsBySubjectFragment", "No teams found for subject: $title")
                        }
                    }
                } else {
                    Log.e("TeamDetailsBySubjectFragment", "Error: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("TeamDetailsBySubjectFragment", "Exception: ${e.message}", e)
                teammateNameAdapter.updateData(emptyList())
            }
        }
    }

    // 팀 종료를 위한 Retrofit 통신 메서드
    private fun completeTeam(teamId: Int) {
        val service = RetrofitImpl.authenticatedRetrofit.create(TeamService::class.java)
        val call = service.completeTeam(teamId.toLong()) // teamId를 Long으로 변환하여 전달

        call.enqueue(object : Callback<BaseResponse<Void>> {
            override fun onResponse(
                call: Call<BaseResponse<Void>>,
                response: Response<BaseResponse<Void>>
            ) {
                if (response.isSuccessful) {
                    val baseResponse = response.body()
                    if (baseResponse != null && baseResponse.success) {
                        Log.d("TeamDetailsBySubjectFragment", "팀플종료 성공")

                        val tvFinishTeam: TextView = view?.findViewById(R.id.tv_home_team_finish) ?: return
                        tvFinishTeam.visibility = View.INVISIBLE
                    } else {
                        Log.e("TeamDetailsBySubjectFragment", "팀플 종료 실패. Message: ${baseResponse?.message}")
                    }
                } else {
                    Log.e("TeamDetailsBySubjectFragment", "팀플 종료 실패. Error code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<BaseResponse<Void>>, t: Throwable) {
                Log.e("TeamDetailsBySubjectFragment", "팀플 종료 실패. Exception: ${t.message}", t)
            }
        })
    }

    private fun replaceFragment(fragment: Fragment, bundle: Bundle?) {
        fragment.arguments = bundle
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fl_content, fragment)
            .commit()
    }
}
