package com.example.instaapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instaapp.R;
import com.example.instaapp.model.PostModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PostRecycleViewAdapter extends RecyclerView.Adapter<PostRecycleViewAdapter.ViewHolder>{

    private static final String TAG = "PostRecyclerViewAdapter";

    ArrayList<PostModel> postModelArrayList;
    Context context;


    public PostRecycleViewAdapter()
    {
        postModelArrayList=new ArrayList<>();
    }

    public void setData(ArrayList<PostModel> postModelArrayList, Context context)
    {
        this.context=context;
        this.postModelArrayList=postModelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View postView=layoutInflater.inflate(R.layout.layout_postscroller,parent,false);
        return new PostRecycleViewAdapter.ViewHolder(postView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG,"OnBindViewHolderCalled");

        PostModel post=postModelArrayList.get(position);
        holder.name.setText(post.getName());
        Picasso.get().load(post.getImage()).into(holder.image);

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Liked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {

        return postModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

         image=itemView.findViewById(R.id.postimage);
         name=itemView.findViewById(R.id.postimage_name);



        }
    }
}