package com.android.buddy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class listAdapter extends RecyclerView.Adapter<listAdapter.MyViewHolder> {

    Context context;
    ArrayList<User> userArrayList;

    public listAdapter(Context context, ArrayList<User> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public listAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.recycle_listitem, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull listAdapter.MyViewHolder holder, int position) {

        User user = userArrayList.get(position);

        holder.friendName.setText(user.friendName);
        holder.friendEmail.setText(user.friendEmail);

    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    interface OnItemClickListener{
        void onItemClick(View v, int position); //뷰와 포지션값
        void onDeleteClick(View v, int position);
        void onInfoClick(View v, int position);
    }

    private OnItemClickListener mListener = null;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView friendName, friendEmail;
        Button deleteBtn, infoBtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            friendName = itemView.findViewById(R.id.friendName);
            friendEmail = itemView.findViewById(R.id.friendEmail);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
            infoBtn = itemView.findViewById(R.id.infoBtn);

            itemView.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position!=RecyclerView.NO_POSITION){
                        if (mListener!=null){
                            mListener.onItemClick(view,position);
                        }
                    }
                }
            });

            deleteBtn.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position!=RecyclerView.NO_POSITION){
                        if (mListener!=null){
                            mListener.onDeleteClick(view, position);
                        }
                    }
                }
            });

            infoBtn.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position!=RecyclerView.NO_POSITION){
                        if (mListener!=null){
                            mListener.onInfoClick(view, position);
                        }
                    }
                }
            });
        }

    }
}
