package com.ssu.bilda.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ssu.bilda.R
import com.ssu.bilda.data.entity.ItemSubject
import com.ssu.bilda.databinding.ItemRcvHomeAddSubjectBinding

class AddSubjectAdapter(
    private val dataList: List<ItemSubject>,
    private val subjectTitle: List<String>,
    private val professor: List<String>
) : RecyclerView.Adapter<AddSubjectAdapter.SubjectViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        val binding = ItemRcvHomeAddSubjectBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SubjectViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) {
        val currentItem = dataList[position]

        holder.bind(currentItem) // 수정된 부분: bind 함수로 currentItem을 전달
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class SubjectViewHolder(val binding: ItemRcvHomeAddSubjectBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ItemSubject) {
            binding.tvHomeAddSubject.text = item.subjectName
            binding.tvHomeAddProfessor.text = item.professorName
        }
    }
}


