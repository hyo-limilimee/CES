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

class SubjectStatusFragment : Fragment() {

    private lateinit var teammateNameAdapter: TeammateNameAdapter

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

        val tvSubjectName: TextView = view.findViewById(R.id.tv_subject_name)
        tvSubjectName.text = title

        val tvEvaluationName: TextView = view.findViewById(R.id.tv_evaluation_name)
        val userName = UserSharedPreferences.getUserName(requireContext())
        tvEvaluationName.text = userName

        val recyclerView: RecyclerView = view.findViewById(R.id.rv_teammate_name_list)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        teammateNameAdapter = TeammateNameAdapter(requireContext(), emptyList())
        recyclerView.adapter = teammateNameAdapter

        fetchTeammateNames(title)

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        teammateNameAdapter.setOnItemClickListener { selectedMember ->
            Log.d(
                "SubjectStatusFragment",
                "Selected Member ID: ${selectedMember.userId}, Name: ${selectedMember.name}"
            )
            GlobalScope.launch(Dispatchers.Main) {
                try {
                    val response = RetrofitImpl.authenticatedRetrofit
                        .create(EvaluationStatusService::class.java)
                        .getEvaluationStatus(currentTeamId.toLong())

                    if (response.isNotEmpty()) {
                        val evaluationStatus = response[0]
                        Log.d(
                            "SubjectStatusFragment",
                            "Has Evaluated: ${evaluationStatus.hasEvaluated}"
                        )
                        if (evaluationStatus.hasEvaluated) {
                            // 토스트 메시지 보여주기
                            Toast.makeText(requireContext(), "이미 평가한 사람입니다", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            // 평가되지 않은 경우, 번들 만들어서 Fragment 전환
                            val bundle = Bundle()
                            bundle.putInt("selectedMemberId", selectedMember.userId)
                            bundle.putString("selectedMemberName", selectedMember.name)

                            val teammateEvaluationFragment = TeammateEvalutionFragment()
                            teammateEvaluationFragment.arguments = bundle
                            replaceFragment(teammateEvaluationFragment, bundle)
                        }
                    }
                } catch (e: Exception) {
                    // 예외 처리
                    Log.e("SubjectStatusFragment", "예외: ${e.message}", e)
                    Toast.makeText(requireContext(), "다시 시도 해주세요", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private var currentTeamId: Int = 0 // 팀아이디 초기화
    private fun fetchTeammateNames(title: String) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val response = RetrofitImpl.authenticatedRetrofit
                    .create(EvaluationService::class.java)
                    .getUserTeams()

                Log.d("SubjectStatusFragment", "Raw Response: ${response.raw()}")

                if (response.isSuccessful) {
                    val teamsWithMembers = response.body()?.result
                    teamsWithMembers?.let { teams ->
                        // 일치하는 팀 찾기
                        val selectedTeams = teams.filter { team ->
                            team.subjectTitle == title
                        }

                        if (selectedTeams.isNotEmpty()) {
                            // 여러 팀이 일치할 수 있으므로 첫 번째 팀을 선택
                            val selectedTeam = selectedTeams.first()

                            // 선택된 팀의 멤버들을 리사이클러뷰에 추가
                            val teamMembers = selectedTeam.members
                            teammateNameAdapter.updateData(teamMembers)

                            currentTeamId = selectedTeam.teamId

                            val responseBody = response.body()?.toString()
                            Log.d("SubjectStatusFragment", "Response Body: $responseBody")
                        } else {
                            Log.d("SubjectStatusFragment", "No teams found for subject: $title")
                        }
                    }
                } else {
                    Log.e("SubjectStatusFragment", "Error: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("SubjectStatusFragment", "Exception: ${e.message}", e)
                teammateNameAdapter.updateData(emptyList())
            }
        }
    }

    private fun replaceFragment(fragment: Fragment, bundle: Bundle?) {
        fragment.arguments = bundle
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fl_content, fragment)
            .commit()
    }

}
