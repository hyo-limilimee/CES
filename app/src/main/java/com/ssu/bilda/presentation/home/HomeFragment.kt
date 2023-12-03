package com.ssu.bilda.presentation.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssu.bilda.R
import com.ssu.bilda.data.common.Subject
import com.ssu.bilda.data.common.SubjectWithTeamStatus
import com.ssu.bilda.data.remote.response.BaseResponse
import com.ssu.bilda.data.remote.RetrofitImpl
import com.ssu.bilda.data.remote.UserSharedPreferences
import com.ssu.bilda.data.remote.response.SignInResponse
import com.ssu.bilda.data.remote.response.UserSubjectResponse
import com.ssu.bilda.data.service.SubjectService
import com.ssu.bilda.data.service.UserService
import com.ssu.bilda.databinding.FragmentHomeBinding
import com.ssu.bilda.presentation.adapter.UserSubjectAdapter
import com.ssu.bilda.presentation.teambuild.TeamBuildOverviewFragment
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: UserSubjectAdapter
    private lateinit var subjectsList: List<SubjectWithTeamStatus>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        recyclerView = binding.root.findViewById(R.id.rcv_home_subject)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = UserSubjectAdapter(emptyList())
        recyclerView.adapter = adapter

        lifecycleScope.launch {
            fetchHomeUserSubjects()
        }

        adapter.setOnItemClickListener { selectedSubject ->
            val hasTeam = selectedSubject.hasTeam

            val fragment = if (hasTeam) {
                TeamBuildOverviewFragment()
            } else {
                TeamDetailsBySubjectFragment()
            }

//            val bundle = Bundle()
//            bundle.putString("subjectId", selectedSubject.subjectId)
//            fragment.arguments = bundle

            // 백 스택에서 모든 기존 프래그먼트를 제거하고 새로운 프래그먼트를 새로운 스택에 추가
            parentFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            parentFragmentManager.beginTransaction()
                .replace(R.id.home, fragment) // 기존 홈 프래그먼트를 새로운 프래그먼트로 대체
                .addToBackStack(null)
                .commit()

        }

        return binding.root
    }


    private fun fetchHomeUserSubjects() {
        val subjectService = RetrofitImpl.authenticatedRetrofit.create(SubjectService::class.java)
        val call = subjectService.getUserSubjects()

        call.enqueue(object : Callback<UserSubjectResponse> {
            override fun onResponse(
                call: Call<UserSubjectResponse>,
                response: Response<UserSubjectResponse>
            ) {
                if (response.isSuccessful) {
                    val subjectResponse = response.body()
                    if (subjectResponse?.success == true) {
                        val subjects = subjectResponse.result ?: emptyList()
                        subjectsList = subjects
                        adapter.updateData(subjectsList)
                        Log.d("HomeFragment", "유저가 속한 과목 불러오기 성공")
                    } else {
                        Log.e("HomeFragment", "유저가 속한 과목 불러오기 실패 - ${subjectResponse?.code}: ${subjectResponse?.message}")
                        // 실패한 경우 처리 로직 추가
                    }
                } else {
                    Log.e("HomeFragment", "유저가 속한 과목 불러오기 실패 - ${response.code()}: ${response.message()}")
                    // 오류 처리
                }
            }

            override fun onFailure(call: Call<UserSubjectResponse>, t: Throwable) {
                Log.e("HomeFragment", "유저가 속한 과목 불러오기 실패 - 네트워크 오류: ${t.message}")
                // 네트워크 오류 처리 로직 추가
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnSubject: Button = binding.btnSubject


        btnSubject.setOnClickListener {

            val addSubjectFragment = AddSubjectFragment()


            parentFragmentManager.beginTransaction()
                .replace(R.id.home, addSubjectFragment)
                .addToBackStack(null)
                .commit()
        }

        // API 호출
        val userService = RetrofitImpl.authenticatedRetrofit.create(UserService::class.java)
        val call = userService.getUserHome()

        call.enqueue(object : Callback<BaseResponse<SignInResponse>> {
            override fun onResponse(
                call: Call<BaseResponse<SignInResponse>>,
                response: Response<BaseResponse<SignInResponse>>
            ) {
                if (response.isSuccessful) {
                    val code = response.body()?.code
                    if (code == 200) {
                        Log.d("homefragment", "유저정보 불러오기 성공")
                        val signInResponse: SignInResponse? = response.body()?.result
                        signInResponse?.let { signIn ->
                            val name = signIn.name
                            val nickname = signIn.nickname
                            val studentId = signIn.studentId
                            val department = signIn.department

                            binding.tvHomeName.text = name

                            // SharedPreferences에 유저 정보 저장
                            UserSharedPreferences.setUserName(requireContext(), name)
                            UserSharedPreferences.setUserNickname(requireContext(), nickname)
                            UserSharedPreferences.setUserStId(requireContext(), studentId)
                            department?.let {
                                UserSharedPreferences.setUserDep(requireContext(), it)
                            }
                        }
                    } else {
                        Log.d("homefragment", "유저정보불러오기 실패")
                    }
                } else {
                    // 오류 처리
                }
            }

            override fun onFailure(call: Call<BaseResponse<SignInResponse>>, t: Throwable) {
                Log.e("homefragment", "네트워크 오류: ${t.message}")
            }
        })
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
