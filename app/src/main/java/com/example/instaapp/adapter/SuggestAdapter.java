package com.example.instaapp.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instaapp.R;
import com.example.instaapp.model.SuggestModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class SuggestAdapter extends RecyclerView.Adapter<SuggestAdapter.ViewHolder>
{
    private static final String TAG = "SuggestAdapter";
    ArrayList<SuggestModel> suggestModelArrayList;
    Context context;


    public SuggestAdapter(){
        suggestModelArrayList=new ArrayList<>();
    }

    public void setData(ArrayList<SuggestModel> suggestModelArrayList, Context context)
    {
        this.context=context;
        this.suggestModelArrayList=suggestModelArrayList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View suggestView=layoutInflater.inflate(R.layout.suggest_layout,parent,false);
        return new SuggestAdapter.ViewHolder(suggestView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Log.d(TAG, "onBindViewHolder: called.");

        SuggestModel suggestModel=suggestModelArrayList.get(position);
        holder.name.setText(suggestModel.getName());
        Picasso.get().load(suggestModel.getImage()).into(holder.image);

        holder.suggestButton.setOnClickListener(v -> {
//                Intent intent=new Intent(context, GalleryActivity.class);
//                intent.putExtra("image", suggestModel.getImage());
//                intent.putExtra("name",suggestModel.getName());
//                context.startActivity(intent);

            Toast.makeText(context,"follow", Toast.LENGTH_SHORT).show();

        });

    }

    @Override
    public int getItemCount() {
        return suggestModelArrayList.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder
    {

        CircleImageView image;
        TextView name;
        Button suggestButton;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.suggest_image);
            name = itemView.findViewById(R.id.sugestName);
            suggestButton=itemView.findViewById(R.id.follow);

        }
    }
}
