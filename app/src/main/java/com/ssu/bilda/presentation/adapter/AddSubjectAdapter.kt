package com.ssu.bilda.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ssu.bilda.R
import com.ssu.bilda.data.common.Subject
import com.ssu.bilda.data.common.SubjectWithTeamStatus

class AddSubjectAdapter(private var subjects: List<Subject>) :
    RecyclerView.Adapter<AddSubjectAdapter.SubjectViewHolder>() {

    private var onItemClick: ((View) -> Unit)? = null

    fun setOnItemClickListener(listener: (View) -> Unit) {
        onItemClick = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rcv_home_add_subject, parent, false)
        return SubjectViewHolder(view)
    }

    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) {
        val subject = subjects[position]
        holder.bind(subject)
    }

    override fun getItemCount(): Int = subjects.size

    fun updateData(newSubjects: List<Subject>) {
        subjects = newSubjects
        notifyDataSetChanged()
    }

    inner class SubjectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.tv_home_add_subject)
        private val professor: TextView = itemView.findViewById(R.id.tv_home_add_professor)

        fun bind(subject: Subject) {
            title.text = subject.title
            professor.text = subject.professor

        }
    }
}