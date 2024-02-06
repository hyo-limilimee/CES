package com.ssu.bilda.presentation.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.ssu.bilda.R
import com.ssu.bilda.data.common.TeamMemberWithNickName
import com.ssu.bilda.presentation.teambuild.ProfileReferFragment

class TeammateProfileAdapter(private val teammateList: List<TeamMemberWithNickName>) :
    RecyclerView.Adapter<TeammateProfileAdapter.TeammateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeammateViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rcv_teammate_profile, parent, false)
        return TeammateViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: TeammateViewHolder, position: Int) {
        val teammate = teammateList[position]

        // TextView에 팀원 이름 설정
        holder.tvTeammateName.text = teammate.nickName

        // "프로필 열람" 버튼에 대한 OnClickListener 설정
        holder.flProfileShowBtn.setOnClickListener {
            // ProfileReferFragment를 열고 userId를 넘김
            val fragmentManager = (holder.itemView.context as FragmentActivity).supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()

            // ProfileReferFragment의 실제 클래스 이름으로 교체
            val profileReferFragment = ProfileReferFragment()

            // userId를 인자로 전달 (userId를 String으로 변환)
            val bundle = Bundle()
            bundle.putString("userId", teammate.userId.toString())
            profileReferFragment.arguments = bundle

            // Fragment 트랜잭션 수행
            fragmentTransaction.replace(R.id.fl_content, profileReferFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun getItemCount(): Int {
        return teammateList.size
    }

    class TeammateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTeammateName: TextView = itemView.findViewById(R.id.tv_teammate_name)
        val flProfileShowBtn: FrameLayout = itemView.findViewById(R.id.fl_ic_profile_show_btn)
    }
}