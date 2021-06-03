package com.example.githubapp2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.githubapp2.R;
import com.example.githubapp2.model.FollowerModel;

import java.util.ArrayList;
import java.util.List;

public class AdapterFollower extends RecyclerView.Adapter<AdapterFollower.ListViewHolder>  {
    private List<FollowerModel> follower;
    private final Context context;

    public AdapterFollower(Context context, ArrayList<FollowerModel> follower) {
        this.context = context;
        this.follower = follower;
    }

    @NonNull
    @Override
    public AdapterFollower.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_follower, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterFollower.ListViewHolder holder, int position) {
        holder.username.setText(follower.get(position).getLogin());
        Glide.with(context).load(follower.get(position).getAvatar_url()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return follower.size();
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {
        final TextView username;
        final ImageView img;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.usernameFollower);
            img = itemView.findViewById(R.id.imgFollower);
        }
    }
}
