package com.ssu.bilda.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ssu.bilda.R
import com.ssu.bilda.data.common.SubjectWithTeamStatus

class UserSubjectAdapter(private var userSubjects: List<SubjectWithTeamStatus>) :
    RecyclerView.Adapter<UserSubjectAdapter.userSubjectViewHolder>() {

    private var onItemClick: ((SubjectWithTeamStatus) -> Unit)? = null

    fun setOnItemClickListener(listener: (SubjectWithTeamStatus) -> Unit) {
        onItemClick = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): userSubjectViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rcv_home, parent, false)
        return userSubjectViewHolder(view)
    }

    override fun onBindViewHolder(holder: userSubjectViewHolder, position: Int) {
        val subject = userSubjects[position]
        holder.bind(subject)
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(subject)
        }
    }

    override fun getItemCount(): Int = userSubjects.size

    fun updateData(newSubjects: List<SubjectWithTeamStatus>) {
        userSubjects = newSubjects
        notifyDataSetChanged()
    }

    inner class userSubjectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.tv_item_home_subject)

        fun bind(subject: SubjectWithTeamStatus) {
            textView.text = subject.title

             }
    }
}