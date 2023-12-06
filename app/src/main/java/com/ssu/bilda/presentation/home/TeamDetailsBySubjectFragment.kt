import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ssu.bilda.databinding.FragmentTeamDetailsBySubjectBinding

//package com.ssu.bilda.presentation.home
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.ssu.bilda.data.remote.RetrofitImpl
//import com.ssu.bilda.data.remote.response.TeamInfoResponse
//import com.ssu.bilda.data.remote.response.TeamResponseDTO
//import com.ssu.bilda.data.service.TeamService
//import com.ssu.bilda.databinding.FragmentTeamDetailsBySubjectBinding // 뷰 바인딩을 가져옵니다.
//import com.ssu.bilda.presentation.adapter.TeamMembersAdapter
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
class TeamDetailsBySubjectFragment : Fragment() {
//
//    private val retrofit = RetrofitImpl.authenticatedRetrofit // 토큰이 필요한 Retrofit 객체
    private var _binding: FragmentTeamDetailsBySubjectBinding? = null
    private val binding get() = _binding!! // 뷰 바인딩 인스턴스를 가져옵니다.

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTeamDetailsBySubjectBinding.inflate(inflater, container, false)
        return binding.root
    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        val teamId = "YOUR_TEAM_ID_HERE" // 팀 ID 가져오기
//        fetchTeamInfo(teamId.toLong())
//    }
//
//    private fun fetchTeamInfo(teamId: Long) {
//        val apiService = retrofit.create(TeamService::class.java)
//        apiService.getTeamInfo(teamId).enqueue(object : Callback<TeamInfoResponse> {
//            override fun onResponse(call: Call<TeamResponseDTO>, response: Response<TeamInfo>) {
//                if (response.isSuccessful) {
//                    val teamResponse = response.body()
//                    teamResponse?.let {
//                        updateRecyclerView(it)
//                    }
//                } else {
//                    // Handle error cases
//                }
//            }
//
//            override fun onFailure(call: Call<TeamResponseDTO>, t: Throwable) {
//                // Handle failure cases
//            }
//        })
//    }
//
//    private fun updateRecyclerView(teamResponse: TeamResponseDTO) {
//        val members = mutableListOf(teamResponse.leaderName to "Leader") // 리더 추가
//
//        teamResponse.members.forEach {
//            members.add(it.name to "Member")
//        }
//
//        val adapter = TeamMembersAdapter(members)
//        binding.rcvHomeTeamMembers.layoutManager = LinearLayoutManager(requireContext())
//        binding.rcvHomeTeamMembers.adapter = adapter
//
//        binding.tvHomeSubject.text = teamResponse.subjectTitle // subjectTitle 반영
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null // 뷰 바인딩 인스턴스를 정리합니다.
//    }
}
