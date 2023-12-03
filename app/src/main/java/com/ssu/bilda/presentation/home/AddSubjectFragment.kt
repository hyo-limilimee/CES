package com.ssu.bilda.presentation.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.data.RadarData
import com.github.mikephil.charting.data.RadarDataSet
import com.github.mikephil.charting.data.RadarEntry
import com.ssu.bilda.R
import com.ssu.bilda.data.common.Subject
import com.ssu.bilda.data.remote.RetrofitImpl
import com.ssu.bilda.data.remote.response.BaseResponse
import com.ssu.bilda.data.service.EvaluationService
import com.ssu.bilda.data.service.SubjectService
import com.ssu.bilda.data.service.UserService
import com.ssu.bilda.databinding.FragmentAddSubjectBinding
import com.ssu.bilda.databinding.ItemRcvHomeAddSubjectBinding
import com.ssu.bilda.presentation.adapter.AddSubjectAdapter
import com.ssu.bilda.presentation.adapter.SubjectAdapter
import com.ssu.bilda.presentation.evaluate.SubjectStatusFragment
import com.ssu.bilda.presentation.mypage.ProfileFragment
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException


class AddSubjectFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AddSubjectAdapter

    private val subjectService: SubjectService by lazy {
        RetrofitImpl.authenticatedRetrofit.create(SubjectService::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_add_subject, container, false)


        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.rcv_add_subject)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = AddSubjectAdapter(emptyList()) // Initialize with an empty list
        recyclerView.adapter = adapter

        // Fetch subjects using Retrofit
        fetchAddSubjects()

        // SubjectAdapter의 클릭 리스너 설정
        adapter.setOnItemClickListener { view ->
            // 아이템이 클릭되었을 때 수행할 동작 구현
            replaceFragment(HomeFragment())
        }


        return view
    }
    private fun  fetchAddSubjects() {

        // Make the API call using Coroutine
        lifecycleScope.launch {
            try {
                val response = subjectService.getUserDepartmentSubjects()

                // Log the response
                Log.d("addsubjectFragment", "Response: $response")

                if (response.success) {
                    // Update the adapter with the received subjects
                    adapter.updateData(response.result)
                    Log.d("addsubjectFragment", "과목 불러오기 성공")
                } else {
                    // Handle error
                    Log.e("addsubjectFragment", "과목 불러오기 실패 - ${response.code}: ${response.message}")
                    // You might want to show an error message or handle it in some way
                }
            } catch (e: Exception) {
                e.printStackTrace()
                // Handle network error
                Log.e("addsubjectFragment", "과목 불러오기 실패 - 네트워크 오류: ${e.message}")
            }
        }
    }
    private fun replaceFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fl_content, fragment)
            .commit()
    }
}