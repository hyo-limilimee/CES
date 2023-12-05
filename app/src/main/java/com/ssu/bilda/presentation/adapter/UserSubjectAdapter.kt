package com.ssu.bilda.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ssu.bilda.R
import com.ssu.bilda.data.common.SubjectWithTeamStatus

class UserSubjectAdapter(private var subjects: List<SubjectWithTeamStatus>) :
    RecyclerView.Adapter<UserSubjectAdapter.SubjectViewHolder>() {

    private var onItemClick: ((SubjectWithTeamStatus) -> Unit)? = null

    fun setOnItemClickListener(listener: (SubjectWithTeamStatus) -> Unit) {
        onItemClick = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rcv_home ,parent, false)
        return SubjectViewHolder(view)
    }

    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) {
        val subject = subjects[position]
        holder.bind(subject)
    }

    override fun getItemCount(): Int = subjects.size

    fun updateData(newSubjects: List<SubjectWithTeamStatus>) {
        subjects = newSubjects
        notifyDataSetChanged()
    }

    inner class SubjectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.tv_item_home_subject)

        fun bind(subject: SubjectWithTeamStatus) {
            textView.text = subject.title

        }
    }
}
