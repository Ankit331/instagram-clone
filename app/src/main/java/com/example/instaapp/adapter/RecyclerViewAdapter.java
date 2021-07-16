package com.example.instaapp.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instaapp.GalleryActivity;
import com.example.instaapp.R;
import com.example.instaapp.model.StatusModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>  {

    ArrayList<StatusModel> statusModelArrayList;
    Context context;


    public RecyclerViewAdapter()
    {
        statusModelArrayList=new ArrayList<>();
    }

    public void setData(ArrayList<StatusModel> statusModelArrayList, Context context)
    {
        this.context=context;
        this.statusModelArrayList=statusModelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View statusView=layoutInflater.inflate(R.layout.layout_status,parent,false);
        return new ViewHolder(statusView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        StatusModel status=statusModelArrayList.get(position);
        holder.name.setText(status.getName());
       Picasso.get().load(status.getImage()).into(holder.image);

               holder.image.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       Intent intent=new Intent(context, GalleryActivity.class);
                       intent.putExtra("image", status.getImage());
                       intent.putExtra("name",status.getName());
                       context.startActivity(intent);

                   }
               });


//        Glide.with(context)    // Glide Library for Decoding and Feching Images
//                .asBitmap()
//                .load(status.getImage())
//                .into(holder.image);


    }

    @Override
    public int getItemCount()
    {
        return statusModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        CircleImageView image;
        TextView name;

        public ViewHolder(View itemView)
        {
            super(itemView);
            image = itemView.findViewById(R.id.story_image);
            name = itemView.findViewById(R.id.storyname);

        }
    }



}

