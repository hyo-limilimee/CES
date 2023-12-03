package com.ssu.bilda.presentation.evaluate

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssu.bilda.R
import com.ssu.bilda.data.common.EvaluationTeam
import com.ssu.bilda.data.remote.RetrofitImpl
import com.ssu.bilda.data.remote.UserSharedPreferences
import com.ssu.bilda.data.service.EvaluationService
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

        fetchTeammateNames()

        teammateNameAdapter.setOnItemClickListener { view ->
            // 아이템이 클릭되었을 때 수행할 동작 구현
            replaceFragment(TeammateEvalutionFragment())
        }

        return view
    }

    private fun fetchTeammateNames() {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val response = RetrofitImpl.authenticatedRetrofit
                    .create(EvaluationService::class.java)
                    .getUserTeams()

                Log.d("SubjectStatusFragment", "Raw Response: ${response.raw()}")

                if (response.isSuccessful) {
                    val teamsWithMembers = response.body()?.result
                    teamsWithMembers?.let {
                        val teamMembers = it.flatMap(EvaluationTeam::members)
                        teammateNameAdapter.updateData(teamMembers.map { member -> member.name })

                        val responseBody = response.body()?.toString()
                        Log.d("SubjectStatusFragment", "Response Body: $responseBody")
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
    private fun replaceFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fl_content, fragment)
            .commit()
    }
}
