package com.example.instaapp;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.instaapp.adapter.PostRecycleViewAdapter;
import com.example.instaapp.adapter.RecyclerViewAdapter;
import com.example.instaapp.adapter.SuggestAdapter;
import com.example.instaapp.model.PostModel;
import com.example.instaapp.model.StatusModel;
import com.example.instaapp.model.SuggestModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private static final String TAG = "MainActivity";

    String url = "https://api.jsonbin.io/b/604f20e67ea6546cf3ddf877";
    RecyclerView recyclerView;                                // For status recyclerview
    RecyclerView  suggestRecyclerview;                        // For profile Suggestion
    RecyclerView postRecyclerView;

    RecyclerViewAdapter recyclerViewAdapter;                  //Status Adapter
    SuggestAdapter suggestAdapter;                            //Suugestion Adapter
    PostRecycleViewAdapter postRecycleViewAdapter;            // Post Recyclerview Adapter

    ArrayList<StatusModel> statusModelArrayList;              // status array list
    ArrayList<SuggestModel> suggestModelArrayList;            // Suggestion Array List
    ArrayList<PostModel>   postModelArrayList;                // Post Array List
    
    View v;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_home, container, false);

        // For Status
        recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false));
        recyclerViewAdapter = new RecyclerViewAdapter();
        recyclerView.setAdapter(recyclerViewAdapter);
        statusModelArrayList=new ArrayList<StatusModel>();

        //For Suggestion
        suggestRecyclerview = v.findViewById(R.id.suggestrecycle);
        suggestRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false));
        suggestAdapter = new SuggestAdapter();
        suggestRecyclerview.setAdapter(suggestAdapter);
        suggestModelArrayList=new ArrayList<SuggestModel>();

        //For Post
        postRecyclerView = v.findViewById(R.id.postrecyclerView);
        postRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false));
        postRecycleViewAdapter = new PostRecycleViewAdapter();
        postRecyclerView.setAdapter(postRecycleViewAdapter);
        postModelArrayList=new ArrayList<PostModel>();


        getDataStatus();      //method calling for Status

        getDataSuggestion();  // method calling for Suggestion

        getDataPost();        // method calling for Post

        ImageView messageImage = v.findViewById(R.id.message);  // on click listener for message
        messageImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Sending Message", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

    private void getDataPost()
    {
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(url, (JSONArray response) -> {

            try {
                for (int i=0;i<response.length();i++)
                {
                    JSONObject jsonObject=response.getJSONObject(i);
                    PostModel post=new PostModel();
                    post.setImage(jsonObject.getString("image"));
                    post.setName(jsonObject.getString("name"));
                    postModelArrayList.add(post);
                }
            }
            catch (JSONException e){
                Toast.makeText(getActivity(), "Post Json error", Toast.LENGTH_SHORT).show();
            }
            postRecycleViewAdapter.notifyDataSetChanged();
            postRecycleViewAdapter.setData(postModelArrayList,getActivity());



        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),"  Suggestion Error occured",Toast.LENGTH_SHORT);
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonArrayRequest);
    }



    // GET Data for Status recyclerview
    private void getDataStatus() {

        ProgressDialog progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(url, (JSONArray response) -> {

          try {
              for (int i=0;i<response.length();i++)
              {
                  JSONObject jsonObject=response.getJSONObject(i);
                  StatusModel statusModel=new StatusModel();
                  statusModel.setImage(jsonObject.getString("image"));
                  statusModel.setName(jsonObject.getString("name"));
                  statusModelArrayList.add(statusModel);
              }
          }
          catch (JSONException e){
              Toast.makeText(getActivity(), "Json error", Toast.LENGTH_SHORT).show();
          }
            recyclerViewAdapter.notifyDataSetChanged();
            recyclerViewAdapter.setData(statusModelArrayList,getActivity());
            progressDialog.dismiss();


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(),"Error occured",Toast.LENGTH_SHORT);
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonArrayRequest);
    }


    // For Suggestion
    private void getDataSuggestion()
    {
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(url, (JSONArray response) -> {

            try {
                for (int i=0;i<response.length();i++)
                {
                    JSONObject jsonObject=response.getJSONObject(i);
                    SuggestModel sugggestModel=new SuggestModel();
                    sugggestModel.setImage(jsonObject.getString("image"));
                    sugggestModel.setName(jsonObject.getString("name"));
                    suggestModelArrayList.add(sugggestModel);
                }
            }
            catch (JSONException e){
                Toast.makeText(getActivity(), "Suggestion Json error", Toast.LENGTH_SHORT).show();
            }
            suggestAdapter.notifyDataSetChanged();
            suggestAdapter.setData(suggestModelArrayList,getActivity());

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),"  Suggestion Error occured",Toast.LENGTH_SHORT);
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonArrayRequest);
    }

}

