package com.ssu.bilda.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssu.bilda.data.common.TeamsOfSubject
import com.ssu.bilda.databinding.ItemRcvTeambuildNameBinding

class TeamsAdapter(private var teamsList: List<TeamsOfSubject>) :
    RecyclerView.Adapter<TeamsAdapter.TeamsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamsViewHolder {
        val binding = ItemRcvTeambuildNameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TeamsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TeamsViewHolder, position: Int) {
        holder.bind(teamsList[position])
    }

    override fun getItemCount(): Int {
        return teamsList.size
    }

    fun updateTeams(newTeamsList: List<TeamsOfSubject>) {
        teamsList = newTeamsList
        notifyDataSetChanged()
    }

    inner class TeamsViewHolder(private val binding: ItemRcvTeambuildNameBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(team: TeamsOfSubject) {
            binding.tvTeambuildName.text = team.teamTitle
            binding.tvTeambuildIng.text = team.memberNum.toString()
            binding.tvTeambuildMax.text = team.maxMemberNum.toString()
        }
        fun bindData(team: TeamsOfSubject) {
            bind(team)
        }
    }
}