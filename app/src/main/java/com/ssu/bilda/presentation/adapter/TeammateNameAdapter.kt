package com.ssu.bilda.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ssu.bilda.R

class TeammateNameAdapter(private val context: Context, private var teammateNames: List<String>) :
    RecyclerView.Adapter<TeammateNameAdapter.TeammateViewHolder>() {

    private var onItemClick: ((String) -> Unit)? = null

    fun setOnItemClickListener(listener: (String) -> Unit) {
        onItemClick = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeammateViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_rcv_evaluation_teammate_name, parent, false)
        return TeammateViewHolder(view)
    }

    override fun onBindViewHolder(holder: TeammateViewHolder, position: Int) {
        val teammateName = teammateNames[position]
        holder.bind(teammateName)
    }

    override fun getItemCount(): Int = teammateNames.size

    fun updateData(newTeammateNames: List<String>) {
        teammateNames = newTeammateNames
        notifyDataSetChanged()
    }

    inner class TeammateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.tv_teammate_name)
        private val teammateLayout: FrameLayout =
            itemView.findViewById(R.id.fl_teammate_evaluation_btn)

        fun bind(teammateName: String) {
            textView.text = teammateName

            teammateLayout.setOnClickListener {
                onItemClick?.invoke(teammateName)
            }
        }
    }
}