package com.example.githubapp3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.githubapp3.R;
import com.example.githubapp3.model.FollowingModel;

import java.util.ArrayList;
import java.util.List;

public class AdapterFollowing extends RecyclerView.Adapter<AdapterFollowing.ListViewHolder>{
    private final List<FollowingModel> following;
    private final Context context;

    public AdapterFollowing(Context context, List<FollowingModel> following) {
        this.context = context;
        this.following = following;
    }

    @NonNull
    @Override
    public AdapterFollowing.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_following, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterFollowing.ListViewHolder holder, int position) {
        holder.username.setText(following.get(position).getLogin());
        Glide.with(context).load(following.get(position).getAvatar_url()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return following.size();
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {
        final TextView username;
        final ImageView img;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.usernameFollowing);
            img = itemView.findViewById(R.id.imgFollowing);
        }
    }
}
