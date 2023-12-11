package com.ssu.bilda.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ssu.bilda.R
import com.ssu.bilda.data.common.TeamsOfSubject

class TeamsAdapter(private var teams: List<TeamsOfSubject>, private val listener: OnItemClickListener) :
    RecyclerView.Adapter<TeamsAdapter.TeamsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rcv_teambuild_name, parent, false)
        return TeamsViewHolder(view)
    }

    override fun onBindViewHolder(holder: TeamsViewHolder, position: Int) {
        val team = teams[position]
        holder.bind(team)
        holder.itemView.setOnClickListener {
            listener.onItemClick(team.teamId)
        }
    }

    override fun getItemCount(): Int = teams.size

    fun updateTeams(newTeams: List<TeamsOfSubject>) {
        teams = newTeams
        notifyDataSetChanged()
    }

    inner class TeamsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val teamName: TextView = itemView.findViewById(R.id.tv_teambuild_name)
        private val teamIng: TextView = itemView.findViewById(R.id.tv_teambuild_ing)
        private val teamSlash: TextView = itemView.findViewById(R.id.tv_teambuild_slash)
        private val teamMax: TextView = itemView.findViewById(R.id.tv_teambuild_max)

        fun bind(team: TeamsOfSubject) {
            teamName.text = team.teamTitle
            teamIng.text = team.memberNum.toString()
            teamSlash.text = "/"
            teamMax.text = team.maxMemberNum.toString()
        }
    }

    interface OnItemClickListener {
        fun onItemClick(teamId: Long)
    }
}
