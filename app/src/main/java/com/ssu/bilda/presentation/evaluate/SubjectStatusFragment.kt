package com.ssu.bilda.presentation.evaluate

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssu.bilda.R
import com.ssu.bilda.data.common.EvaluationTeam
import com.ssu.bilda.data.remote.RetrofitImpl
import com.ssu.bilda.data.remote.UserSharedPreferences
import com.ssu.bilda.data.service.EvaluationService
import com.ssu.bilda.data.service.EvaluationStatusService
import com.ssu.bilda.presentation.adapter.TeammateNameAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SubjectStatusFragment : Fragment() {

    private lateinit var teammateNameAdapter: TeammateNameAdapter
    private var teamsWithMembers: List<EvaluationTeam>? = null

    companion object {
        fun newInstance(title: String): SubjectStatusFragment {
            val fragment = SubjectStatusFragment()
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
        val view = inflater.inflate(R.layout.fragment_subject_status, container, false)
        val title = arguments?.getString("title", "") ?: ""
        val userName = UserSharedPreferences.getUserName(requireContext()) // userName 가져오기

        val tvSubjectName: TextView = view.findViewById(R.id.tv_subject_name)
        tvSubjectName.text = title

        val tvEvaluationName: TextView = view.findViewById(R.id.tv_evaluation_name)
        tvEvaluationName.text = userName

        val recyclerView: RecyclerView = view.findViewById(R.id.rv_teammate_name_list)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        teammateNameAdapter = TeammateNameAdapter(requireContext(), emptyList())
        recyclerView.adapter = teammateNameAdapter

        fetchTeammateNames(title, userName) // userName 전달

        teammateNameAdapter.setOnItemClickListener { selectedMember, teamId ->
            val bundle = Bundle().apply {
                putInt("selectedMemberId", selectedMember.userId)
                putString("selectedMemberName", selectedMember.name)
                putInt("teamId", teamId)
            }

            val teammateEvaluationFragment = TeammateEvalutionFragment()
            replaceFragment(teammateEvaluationFragment, bundle)
        }
        return view
    }



    private fun fetchTeammateNames(title: String, userName: String) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val response = RetrofitImpl.authenticatedRetrofit
                    .create(EvaluationService::class.java)
                    .getUserTeams()

                Log.d("SubjectStatusFragment", "Raw Response: ${response.raw()}")

                if (response.isSuccessful) {
                    val teamsWithMembers = response.body()?.result
                    teamsWithMembers?.let { teams ->
                        if (teams.isEmpty()) {
                            Log.d("SubjectStatusFragment", "No teams found.")
                        } else {
                            // Find matching teams
                            val selectedTeams = teams.filter { team ->
                                team.subjectTitle == title
                            }

                            Log.d("SubjectStatusFragment", "Selected Teams: $selectedTeams")

                            if (selectedTeams.isNotEmpty()) {
                                // Select the first matching team
                                val selectedTeam = selectedTeams.first()

                                val teamId = selectedTeam.teamId
                                Log.d("SubjectStatusFragment", "teamId: $teamId")

                                // Filter team members based on names
                                val teamMembers = selectedTeam.members.filterNot { member ->
                                    member.name == userName
                                }

                                teammateNameAdapter.updateData(teamMembers, teamId)

                                val responseBody = response.body()?.toString()
                                Log.d("SubjectStatusFragment", "Response Body: $responseBody")
                            } else {
                                Log.d("SubjectStatusFragment", "No teams found for subject: $title")
                            }
                        }
                    }
                } else {
                    Log.e("SubjectStatusFragment", "Error: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("SubjectStatusFragment", "Exception: ${e.message}", e)
                teammateNameAdapter.updateData(emptyList(), -1) // Pass the default teamId, you can change it accordingly
            }
        }
    }

    private fun fetchTeammateNames(title: String, userId: Int) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val response = RetrofitImpl.authenticatedRetrofit
                    .create(EvaluationService::class.java)
                    .getUserTeams()

                Log.d("SubjectStatusFragment", "Raw Response: ${response.raw()}")

                if (response.isSuccessful) {
                    val teamsWithMembers = response.body()?.result
                    teamsWithMembers?.let { teams ->
                        if (teams.isEmpty()) {
                            Log.d("SubjectStatusFragment", "No teams found.")
                        } else {
                            // 일치하는 팀 찾기
                            val selectedTeams = teams.filter { team ->
                                team.subjectTitle == title
                            }

                            Log.d("SubjectStatusFragment", "Selected Teams: $selectedTeams")

                            if (selectedTeams.isNotEmpty()) {
                                // 여러 팀이 일치할 수 있으므로 첫 번째 팀을 선택
                                val selectedTeam = selectedTeams.first()

                                val teamId = selectedTeam.teamId
                                Log.d("SubjectStatusFragment", "teamId: $teamId")

                                // 선택된 팀의 멤버들을 리사이클러뷰에 추가
                                val teamMembers = selectedTeam.members.filterNot { member ->
                                    member.userId == userId
                                }

                                teammateNameAdapter.updateData(teamMembers, teamId)

                                val responseBody = response.body()?.toString()
                                Log.d("SubjectStatusFragment", "Response Body: $responseBody")
                            } else {
                                Log.d("SubjectStatusFragment", "No teams found for subject: $title")
                            }
                        }
                    }
                } else {
                    Log.e("SubjectStatusFragment", "Error: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("SubjectStatusFragment", "Exception: ${e.message}", e)
                teammateNameAdapter.updateData(emptyList(), -1) // Pass the default teamId, you can change it accordingly
            }
        }
    }



    private fun findTeamIdByMember(teams: List<EvaluationTeam>?, memberId: Int): Int {
        teams?.let { teamsList ->
            for (team in teamsList) {
                for (member in team.members) {
                    if (member.userId == memberId) {
                        return team.teamId
                    }
                }
            }
        }
        return -1
    }


    private fun replaceFragment(fragment: Fragment, bundle: Bundle?) {
        bundle?.let {
            fragment.arguments = it
        }

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fl_content, fragment)
            .commit()
    }
}
