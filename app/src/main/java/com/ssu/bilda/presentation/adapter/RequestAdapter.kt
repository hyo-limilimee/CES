package com.ssu.bilda.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ssu.bilda.R
import com.ssu.bilda.data.common.UserData

class RequestAdapter : RecyclerView.Adapter<RequestAdapter.RequestViewHolder>() {

    private var requestList: List<UserData> = emptyList()

    fun setRequestList(list: List<UserData>) {
        requestList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rcv_request, parent, false)
        return RequestViewHolder(view)
    }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {
        val userData = requestList[position]

        // 여기서 텍스트 뷰에 데이터를 설정합니다.
        holder.requestNameTextView.text = userData.userName
    }

    override fun getItemCount(): Int {
        return requestList.size
    }

    class RequestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val requestNameTextView: TextView = itemView.findViewById(R.id.tv_request_name)
        // 여기에 다른 뷰들을 참조할 수 있도록 선언합니다.
    }
}
