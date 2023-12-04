package com.ssu.bilda.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ssu.bilda.R
import com.ssu.bilda.data.common.TeamMemberWithNickName

class TeammateProfileAdapter(private val teammateList: List<TeamMemberWithNickName>) :
    RecyclerView.Adapter<TeammateProfileAdapter.TeammateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeammateViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rcv_teammate_profile, parent, false)
        return TeammateViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TeammateViewHolder, position: Int) {
        val teammate = teammateList[position]

        // Set the teammate name in the TextView
        holder.tvTeammateName.text = teammate.nickName
    }

    override fun getItemCount(): Int {
        return teammateList.size
    }

    class TeammateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTeammateName: TextView = itemView.findViewById(R.id.tv_teammate_name)
    }
}