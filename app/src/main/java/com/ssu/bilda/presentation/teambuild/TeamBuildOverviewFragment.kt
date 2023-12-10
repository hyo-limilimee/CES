package com.ssu.bilda.presentation.teambuild

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssu.bilda.R
import com.ssu.bilda.data.enums.RecruitmentStatus
import com.ssu.bilda.data.common.TeamsOfSubject
import com.ssu.bilda.data.remote.RetrofitImpl
import com.ssu.bilda.data.remote.response.ResponseDtoListTeamsOfSubjectDTO
import com.ssu.bilda.data.remote.response.TeamsOfSubjectDTO
import com.ssu.bilda.data.service.TeamService
import com.ssu.bilda.presentation.adapter.TeamsAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TeamBuildOverviewFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var teamsAdapter: TeamsAdapter
    private var receivedSubjectCode: Long = 0L
    private lateinit var teamService: TeamService // Retrofit Service

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_team_build_overview, container, false)

        recyclerView = view.findViewById(R.id.rcv_teambuild_name)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        teamsAdapter = TeamsAdapter(emptyList())
        recyclerView.adapter = teamsAdapter

        teamService = RetrofitImpl.authenticatedRetrofit.create(TeamService::class.java)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 이전에 전달된 subjectCode 확인
        receivedSubjectCode = arguments?.getLong("subjectCode") ?: 0
        Log.d("TeamBuildOverview", "받은 subjectCode: $receivedSubjectCode")

        fetchTeamsBySubject(receivedSubjectCode)

        // btn_teambuild_write 클릭 이벤트 처리
        val btnTeambuildWrite = view.findViewById<Button>(R.id.btn_teambuild_write)
        btnTeambuildWrite.setOnClickListener {
            navigateToTeambuildWritingFragment(receivedSubjectCode)
        }

    }

    private fun fetchTeamsBySubject(subjectId: Long) {
        teamService.getTeamsBySubject(subjectId).enqueue(object : Callback<ResponseDtoListTeamsOfSubjectDTO> {
            override fun onResponse(
                call: Call<ResponseDtoListTeamsOfSubjectDTO>,
                response: Response<ResponseDtoListTeamsOfSubjectDTO>
            ) {
                if (response.isSuccessful) {
                    val teamsResponse = response.body()
                    teamsResponse?.let { teams ->
                        val teamsListDTO: List<TeamsOfSubjectDTO> = teams.result
                        val teamsList: MutableList<TeamsOfSubject> = mutableListOf()

                        for (teamDTO in teamsListDTO) {
                            val team = TeamsOfSubject(
                                teamDTO.teamId,
                                teamDTO.teamTitle,
                                teamDTO.subjectName,
                                RecruitmentStatus.valueOf(teamDTO.recruitmentStatus),
                                teamDTO.memberNum,
                                teamDTO.maxMemberNum
                            )
                            teamsList.add(team)
                        }

                        teamsAdapter = TeamsAdapter(teamsList)
                        recyclerView.adapter = teamsAdapter
                    }
                } else {
                    Log.e("TeamBuildOverview", "응답 실패: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ResponseDtoListTeamsOfSubjectDTO>, t: Throwable) {
                Log.e("TeamBuildOverview", "네트워크 오류: ${t.message}")
            }
        })
    }


    private fun navigateToTeambuildWritingFragment(subjectCode: Long) {
        val teambuildWritingFragment = TeamBuildWritingFragment()
        val bundle = Bundle()
        bundle.putLong("subjectCode", subjectCode)
        Log.d("TeamBuildOverview", "보낼 subjectCode: $subjectCode")  // 수정된 부분
        teambuildWritingFragment.arguments = bundle
        activity?.supportFragmentManager?.beginTransaction()?.apply {
            replace(R.id.fl_content, teambuildWritingFragment)
            addToBackStack(null)
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            commit()
        }
    }
}