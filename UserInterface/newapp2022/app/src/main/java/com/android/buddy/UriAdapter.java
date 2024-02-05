package com.android.buddy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class UriAdapter extends RecyclerView.Adapter<UriAdapter.MyViewHolder> {

   Context context;
   ArrayList<Model> uriArrayList = new ArrayList<>();

   public UriAdapter(Context context, ArrayList<Model> uriArrayList) {
      this.context = context;
      this.uriArrayList = uriArrayList;
   }

   @NonNull
   @Override
   public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

      View v = LayoutInflater.from(context).inflate(R.layout.image_item, parent, false);

      return new MyViewHolder(v);
   }

   @Override
   public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

      Glide.with(context).load(uriArrayList.get(position).getImageUri()).into(holder.image);
   }

   @Override
   public int getItemCount() {
      return uriArrayList.size();
   }

   public class MyViewHolder extends RecyclerView.ViewHolder {

      ImageView image;

      public MyViewHolder(@NonNull View itemView) {
         super(itemView);

         image = itemView.findViewById(R.id.image);
      }

   }
}
