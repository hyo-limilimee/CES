package com.ssu.bilda.presentation.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssu.bilda.R
import com.ssu.bilda.data.common.Subject
import com.ssu.bilda.data.common.UserSubject
import com.ssu.bilda.data.remote.RetrofitImpl
import com.ssu.bilda.data.remote.response.Hresponse
import com.ssu.bilda.data.service.SubjectService
import com.ssu.bilda.presentation.adapter.AddSubjectAdapter
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException


class AddSubjectFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AddSubjectAdapter
    private lateinit var subjectsList: List<Subject>

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
            val position = recyclerView.getChildAdapterPosition(view)
            val selectedSubject =  subjectsList[position]

            // 클릭한 과목의 코드를 서버에 전송
            addUserSubjectToServer(selectedSubject.subjectCode)
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
                    subjectsList = response.result
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

    // 클릭한 과목의 코드를 서버에 전송 api
    private fun addUserSubjectToServer(subjectCode: Long) {
        subjectService.addUserSubject(subjectCode).enqueue(object :
            Callback<Hresponse<UserSubject>> {
            override fun onResponse(
                call: Call<Hresponse<UserSubject>>,
                response: Response<Hresponse<UserSubject>>
            ) {
                if (response.isSuccessful && response.body() != null && response.body()?.data != null) {
                    // 성공적으로 요청이 처리된 경우
                    Log.d("AddSubject", "과목 추가 성공")
                    replaceNewHomeFragment(subjectCode)
                } else {
                    // 서버 응답이 null이거나 요청이 실패한 경우
                    val errorMessage = response.message() ?: "Unknown error"
                    Log.e("AddSubject", "과목 추가 실패 - $errorMessage")

                }
            }

            override fun onFailure(call: Call<Hresponse<UserSubject>>, t: Throwable) {
                // 네트워크 오류 또는 요청 실패
                Log.e("AddSubject", "과목 추가 실패 - 네트워크 오류: ${t.message}")
                // 네트워크 오류 시 적절한 처리
            }
        })
    }
    private fun replaceNewHomeFragment(subjectCode: Long) {
        val homeFragment = HomeFragment()
        val bundle = Bundle()
        bundle.putLong("subjectCode", subjectCode)
        homeFragment.arguments = bundle
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fl_content, homeFragment)
            .commit()
    }


}
