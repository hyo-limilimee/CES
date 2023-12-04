package com.ssu.bilda.presentation.teambuild

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ssu.bilda.R
import com.ssu.bilda.data.common.TeamDetail
import com.ssu.bilda.data.remote.RetrofitImpl
import com.ssu.bilda.data.remote.response.TeamInfoResponse
import com.ssu.bilda.data.service.TeamService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TeamBuildPostViewGeneral : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_team_build_post_view_general, container, false)

        // Make API call when the fragment is created
        getTeamInfo()

        return view
    }

    private fun getTeamInfo() {
        val retrofit = RetrofitImpl.authenticatedRetrofit
        val teamService = retrofit.create(TeamService::class.java)

        val teamId = 1L // Replace with the desired teamId
        val call = teamService.getTeamInfo(teamId)

        call.enqueue(object : Callback<TeamInfoResponse> {
            override fun onResponse(
                call: Call<TeamInfoResponse>,
                response: Response<TeamInfoResponse>
            ) {
                if (response.isSuccessful) {
                    val teamInfoResponse = response.body()
                    if (teamInfoResponse != null) {
                        val teamDetail: TeamDetail = teamInfoResponse.result
                        // Handle the team detail data as needed
                        Log.d("TeamDetail", teamDetail.toString())
                    }
                } else {
                    // Handle unsuccessful response
                    Log.e(
                        "API Error",
                        "Code: ${response.code()}, Message: ${response.message()}"
                    )
                }
            }

            override fun onFailure(call: Call<TeamInfoResponse>, t: Throwable) {
                // Handle failure
                Log.e("API Error", "Failed to make API call: ${t.message}")
            }
        })
    }
}
