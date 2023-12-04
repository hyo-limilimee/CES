package com.ssu.bilda.presentation.evaluate

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssu.bilda.R
import com.ssu.bilda.data.remote.RetrofitImpl
import com.ssu.bilda.data.remote.UserSharedPreferences
import com.ssu.bilda.data.service.EvaluationService
import com.ssu.bilda.presentation.adapter.SubjectAdapter
import com.ssu.bilda.presentation.teambuild.ProfileReferFragment
import com.ssu.bilda.presentation.teambuild.TeamBuildWritingFragment
import kotlinx.coroutines.launch

class ProjectStatusFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SubjectAdapter

    private val evaluationService: EvaluationService by lazy {
        RetrofitImpl.authenticatedRetrofit.create(EvaluationService::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_project_status, container, false)

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.rv_subject_list)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = SubjectAdapter(emptyList()) // Initialize with an empty list
        recyclerView.adapter = adapter

        // Fetch subjects using Retrofit
        fetchSubjects()

        // SubjectAdapter의 클릭 리스너 설정
        adapter.setOnItemClickListener { selectedSubject ->
            // 아이템이 클릭되었을 때 수행할 동작 구현
            replaceFragment(SubjectStatusFragment.newInstance(selectedSubject.title))
        }

        // Set userName to the TextView
        val tvEvaluationName: TextView = view.findViewById(R.id.tv_evaluation_name)
        val userName = UserSharedPreferences.getUserName(requireContext())
        tvEvaluationName.text = userName

        // 임시 버튼
        // Add a temporary button to navigate to TeamBuildWritingFragment
        val btnNavigateToTeamBuild: Button = view.findViewById(R.id.btn_temp)
        btnNavigateToTeamBuild.setOnClickListener {
            replaceFragment(ProfileReferFragment())
        }

        return view
    }

    private fun fetchSubjects() {

        // Make the API call using Coroutine
        lifecycleScope.launch {
            try {
                val response = evaluationService.getUserSubjects()

                // Log the response
                Log.d("ProjectStatusFragment", "Response: $response")

                if (response.success) {
                    // Update the adapter with the received subjects
                    adapter.updateData(response.result)
                    Log.d("ProjectStatusFragment", "과목 불러오기 성공")
                } else {
                    // Handle error
                    Log.e("ProjectStatusFragment", "과목 불러오기 실패 - ${response.code}: ${response.message}")
                    // You might want to show an error message or handle it in some way
                }
            } catch (e: Exception) {
                e.printStackTrace()
                // Handle network error
                Log.e("ProjectStatusFragment", "과목 불러오기 실패 - 네트워크 오류: ${e.message}")
            }
        }
    }
    private fun replaceFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fl_content, fragment)
            .commit()
    }
}