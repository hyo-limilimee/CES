package com.ssu.bilda.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ssu.bilda.R
import com.ssu.bilda.data.common.SubjectWithTeamStatus

class SubjectAdapter(private var subjects: List<SubjectWithTeamStatus>) :
    RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rcv_evaluation_subject, parent, false)
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
        private val subjectTextView: TextView = itemView.findViewById(R.id.tv_evaluation_subject)

        fun bind(subject: SubjectWithTeamStatus) {
            // title 필드만 사용하여 TextView에 표시
            subjectTextView.text = subject.title

        }
    }
}
