package com.ssu.bilda.presentation.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssu.bilda.R
import com.ssu.bilda.data.remote.response.BaseResponse
import com.ssu.bilda.data.remote.RetrofitImpl
import com.ssu.bilda.data.remote.UserSharedPreferences
import com.ssu.bilda.data.remote.response.SignInResponse
import com.ssu.bilda.data.service.EvaluationService
import com.ssu.bilda.data.service.UserService
import com.ssu.bilda.presentation.adapter.UserSubjectAdapter
import com.ssu.bilda.presentation.evaluate.SubjectStatusFragment
import com.ssu.bilda.presentation.teambuild.TeamBuildOverviewFragment
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: UserSubjectAdapter
    private var subjectCode: Long = 0 //과목 코드 기본값

    private val evaluationService: EvaluationService by lazy {
        RetrofitImpl.authenticatedRetrofit.create(EvaluationService::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.rcv_home_subject)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = UserSubjectAdapter(emptyList()) // 빈 목록으로 초기화
        recyclerView.adapter = adapter

        // Retrofit을 사용하여 과목 가져오기
        fetchSubjects()


        adapter.setOnItemClickListener { Subject ->
            val hasTeam = Subject.hasTeam

            val fragment = if (hasTeam) {
                TeamDetailsBySubjectFragment()
            } else {
                val subjectCode = Subject.subjectCode // 과목 코드 가져오기
                replaceTeamBuildOverviewFragment(subjectCode)
                TeamBuildOverviewFragment() // 또는 해당 Fragment의 인스턴스 생성
            }

            // 백 스택에서 모든 기존 프래그먼트를 제거하고 새로운 프래그먼트를 새로운 스택에 추가
            parentFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            parentFragmentManager.beginTransaction()
                .replace(R.id.home, fragment) // 기존 홈 프래그먼트를 새로운 프래그먼트로 대체
                .addToBackStack(null)
                .commit()

        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnSubject: Button = view.findViewById(R.id.btn_subject)
        val btnChatBot: Button = view.findViewById(R.id.btn_chatbot)

        btnChatBot.setOnClickListener {
            val intent = Intent(requireContext(), ChatBotActivity::class.java)
            startActivity(intent)
        }

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

                            // 이름 텍스트뷰에 설정
                            view.findViewById<TextView>(R.id.tv_home_name).text = name

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

    private fun fetchSubjects() {
        // Make the API call using Coroutine
        lifecycleScope.launch {
            try {
                val response = evaluationService.getUserSubjects()

                if (response.success) {

                    response.result.firstOrNull()?.let { subject ->
                        subjectCode = subject.subjectCode
                    }
                    // 받은 과목으로 어댑터 업데이트
                    adapter.updateData(response.result)
                    Log.d("ProjectStatusFragment", "과목 불러오기 성공")
                } else {
                    // 에러 처리
                    Log.e(
                        "ProjectStatusFragment",
                        "과목 불러오기 실패 - ${response.code}: ${response.message}"
                    )
                    // 에러 메시지 표시나 적절한 처리를 하면 됩니다.
                }
            } catch (e: Exception) {
                e.printStackTrace()
                // 네트워크 에러 처리
                Log.e("ProjectStatusFragment", "과목 불러오기 실패 - 네트워크 오류: ${e.message}")
            }
        }
    }

    private fun replaceTeamBuildOverviewFragment(subjectCode: Long) {
        val teamBuildOverviewFragment = TeamBuildOverviewFragment()
        val bundle = Bundle()
        bundle.putLong("subjectCode", subjectCode)
        teamBuildOverviewFragment.arguments = bundle
        Log.d("HomeFragment", "전달한 subjectCode: $subjectCode")

        // 홈 프래그먼트를 백 스택에서 제거하고 새 프래그먼트로 대체
        parentFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        parentFragmentManager.beginTransaction()
            .replace(R.id.home, teamBuildOverviewFragment)
            .commit()
    }

}