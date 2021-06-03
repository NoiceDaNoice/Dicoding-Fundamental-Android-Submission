package com.example.githubapp2.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.githubapp2.DetailUser;
import com.example.githubapp2.R;
import com.example.githubapp2.model.UserGithubModel;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class AdapterMain extends RecyclerView.Adapter<AdapterMain.ListViewHolder> {
    private List<UserGithubModel> userData;
    private final Context context;

    public AdapterMain(Context context, List<UserGithubModel> userData) {
        this.context = context;
        this.userData = userData;
    }

    @NonNull
    @Override
    public AdapterMain.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_main, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMain.ListViewHolder holder, int position) {
        holder.username.setText(userData.get(position).getLogin());
        Glide.with(context).load(userData.get(position).getAvatar_url()).into(holder.img);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailUser.class);
            intent.putExtra("login", userData.get(position));
            Log.d(TAG, "onClick: "+userData.get(position));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return userData.size();
    }


    public static class ListViewHolder extends RecyclerView.ViewHolder {
        final TextView username;
        final ImageView img;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            img = itemView.findViewById(R.id.img);
        }
    }

}