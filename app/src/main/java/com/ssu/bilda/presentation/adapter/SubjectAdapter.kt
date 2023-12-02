package com.ssu.bilda.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ssu.bilda.R
import com.ssu.bilda.data.common.Subject

class SubjectAdapter(private val dataList: List<Subject>) :
    RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder>() {

    private var onItemClick: ((View) -> Unit)? = null

    fun setOnItemClickListener(listener: (View) -> Unit) {
        onItemClick = listener
    }

    // 새로운 아이템 뷰 위한 뷰홀더 객체 생성, 초기화
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rcv_evalution_subject, parent, false)
        return SubjectViewHolder(view)
    }

    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) {
        val item = dataList[position]

        // tvSubject에 title을 넣음
        holder.tvSubject.text = item.title
    }

    // getItemCount, getItemId, and other methods remain the same
    override fun getItemCount(): Int = dataList.size

    // 아이템의 각 뷰 홀더, RecyclerView.ViewHolder 클래스 상속 받음
    inner class SubjectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvSubject: TextView = itemView.findViewById(R.id.tv_evaluation_subject)
        private val rightArrowLayout: FrameLayout =
            itemView.findViewById(R.id.fl_ic_gray_right_arrow)

        init {
            rightArrowLayout.setOnClickListener {
                onItemClick?.invoke(rightArrowLayout)
            }
        }
    }
}
