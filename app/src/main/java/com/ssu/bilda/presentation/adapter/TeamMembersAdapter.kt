package com.ssu.bilda.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssu.bilda.databinding.ItemRcvHomeTeamMembersBinding // 뷰 바인딩을 가져옵니다.

class TeamMembersAdapter(private val members: List<Pair<String, String>>) : RecyclerView.Adapter<TeamMembersAdapter.MemberViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        val binding = ItemRcvHomeTeamMembersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MemberViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        val (name, role) = members[position]
        holder.bind(name, role)
    }

    override fun getItemCount(): Int {
        return members.size
    }

    class MemberViewHolder(private val binding: ItemRcvHomeTeamMembersBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(name: String, role: String) {
            binding.tvItemTeamMembers.text = name
            if (role == "Leader") {
                binding.ivLeader.visibility = View.VISIBLE
            } else {
                binding.ivLeader.visibility = View.GONE
            }
        }
    }
}
