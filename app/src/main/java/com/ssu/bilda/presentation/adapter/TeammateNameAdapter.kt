package com.ssu.bilda.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ssu.bilda.R

class TeammateNameAdapter(private val context: Context, private val teammateNameList: List<String>) :
    // RecyclerView.Adapter 클래스 상속 받음 & subjetViewHolder -> 제네릭
    RecyclerView.Adapter<TeammateNameAdapter.TeammateNameViewHolder>() {

    private var onItemClick: ((View) -> Unit)? = null

    fun setOnItemClickListener(listener: (View) -> Unit) {
        onItemClick = listener
    }

    // 새로운 아이템 뷰 위한 뷰홀더 객체 생성, 초기화
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeammateNameViewHolder {
        val view =
            LayoutInflater.from(context)
                .inflate(R.layout.item_rcv_evalution_teammate_name, parent, false)

        return TeammateNameViewHolder(view)
    }

    // 특정 위치의 데이터를 뷰 홀더에 바인딩
    override fun onBindViewHolder(holder: TeammateNameViewHolder, position: Int) {
        val name = teammateNameList[position]
        holder.bind(name)
    }

    // 데이터의 총 아이템 수 반환
    override fun getItemCount(): Int {
        return teammateNameList.size
    }

    //아이템의 각 뷰 홀더, RecyclerView.ViewHolder 클래스 상속 받음
    inner class TeammateNameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.tv_teammate_name)
        private val teammateEvaluationBtnLayout: FrameLayout =
            itemView.findViewById(R.id.fl_teammate_evaluation_btn)

        fun bind(name: String) {
            textView.text = name

            teammateEvaluationBtnLayout.setOnClickListener {
                onItemClick?.invoke(teammateEvaluationBtnLayout)
            }
        }
    }
}
