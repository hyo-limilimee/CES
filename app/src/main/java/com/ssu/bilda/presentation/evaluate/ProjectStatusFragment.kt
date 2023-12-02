package com.ssu.bilda.presentation.evaluate

import android.os.Bundle
import com.ssu.bilda.data.service.SubjectService
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssu.bilda.R
import com.ssu.bilda.data.remote.RetrofitImpl
import com.ssu.bilda.presentation.adapter.SubjectAdapter
import kotlinx.coroutines.launch

class ProjectStatusFragment : Fragment() {

    private val subjectService: SubjectService by lazy {
        RetrofitImpl.authenticatedRetrofit.create(SubjectService::class.java)
    }

    // RecyclerView 어댑터
    private lateinit var adapter: SubjectAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // fragment layout inflate
        val view = inflater.inflate(R.layout.fragment_project_status, container, false)

        // recyclerview 어댑터 초기화
        adapter = SubjectAdapter(emptyList())

        // RecyclerView 초기화 및 어댑터 설정
        val rvSubjectList: RecyclerView = view.findViewById(R.id.rv_subject_list)
        rvSubjectList.layoutManager = LinearLayoutManager(requireContext())
        rvSubjectList.adapter = adapter

        // SubjectAdapter의 클릭 리스너 설정
        adapter.setOnItemClickListener { view ->
            // 아이템이 클릭되었을 때 수행할 동작 구현
            replaceFragment(SubjectStatusFragment())
        }

        // API 호출 및 RecyclerView 갱신
        lifecycleScope.launch {
            try {
                val response = subjectService.getUserSubjects()

                if (response.isSuccessful) {
                    // API 응답 성공 시 RecyclerView 어댑터 갱신
                    val subjectList = response.body()?.result ?: emptyList()
                    adapter.updateData(subjectList)
                } else {
                    // API 응답 실패 시 처리
                    // 예: 사용자에게 메시지 표시 등
                }
            } catch (e: Exception) {
                // Handle network or other exceptions
                e.printStackTrace()
            }
        }

        return view
    }

    private fun replaceFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fl_content, fragment)
            .commit()
    }
}
