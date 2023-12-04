package com.ssu.bilda.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ssu.bilda.R
import com.ssu.bilda.data.common.EvaluationTeamMember

class TeammateNameAdapter(private val context: Context, private var teammates: List<EvaluationTeamMember>) :
    RecyclerView.Adapter<TeammateNameAdapter.TeammateViewHolder>() {

    private var onItemClick: ((EvaluationTeamMember) -> Unit)? = null

    fun setOnItemClickListener(listener: (EvaluationTeamMember) -> Unit) {
        onItemClick = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeammateViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_rcv_evaluation_teammate_name, parent, false)
        return TeammateViewHolder(view)
    }

    override fun onBindViewHolder(holder: TeammateViewHolder, position: Int) {
        val teammate = teammates[position]
        holder.bind(teammate)
    }

    override fun getItemCount(): Int = teammates.size

    fun updateData(newTeammates: List<EvaluationTeamMember>) {
        teammates = newTeammates
        notifyDataSetChanged()
    }

    inner class TeammateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.tv_teammate_name)
        private val teammateLayout: FrameLayout =
            itemView.findViewById(R.id.fl_teammate_evaluation_btn)

        fun bind(teammate: EvaluationTeamMember) {
            textView.text = teammate.name

            teammateLayout.setOnClickListener {
                onItemClick?.invoke(teammate)
            }
        }
    }
}
