package com.example.githubapp3.adapter;

import android.app.Activity;
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
import com.example.githubapp3.CustomOnItemClickListener;
import com.example.githubapp3.DetailUserActivity;
import com.example.githubapp3.R;
import com.example.githubapp3.model.DetailFavUserGithubModel;

import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ListViewHolder>  {
    private final ArrayList<DetailFavUserGithubModel> userData = new ArrayList<>();
    private final Activity activity;

    public FavoriteAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setListNotes(ArrayList<DetailFavUserGithubModel> listNotes) {

        if (userData.size() > 0) {
            this.userData.clear();
        }
        this.userData.addAll(listNotes);

        notifyDataSetChanged();
    }

    public void addItem(DetailFavUserGithubModel detailFavUserGithubModel) {
        this.userData.add(detailFavUserGithubModel);
        notifyItemInserted(userData.size() - 1);
    }

    public void removeItem(int position) {
        this.userData.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, userData.size());
    }

    public ArrayList<DetailFavUserGithubModel> getListNotes() {
        return userData;
    }

    @NonNull
    @Override
    public FavoriteAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_favorite, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.ListViewHolder holder, int position) {
        holder.username.setText(getListNotes().get(position).getUsername());
        Glide.with(holder.itemView).load(getListNotes().get(position).getImage()).into(holder.image);
        holder.itemView.setOnClickListener(new CustomOnItemClickListener(position, (view, position1) -> {
            Intent intent = new Intent(activity, DetailUserActivity.class);
            intent.putExtra(DetailUserActivity.EXTRA_NOTE, userData.get(position1));
            Log.d("TAG", "KELUAR G: "+ DetailUserActivity.EXTRA_NOTE+" "+userData.get(position1));
            activity.startActivity(intent);
        }));
    }

    @Override
    public int getItemCount() {
        return userData.size();
    }

    static class ListViewHolder extends RecyclerView.ViewHolder {
        final TextView username;
        final ImageView image;

        ListViewHolder(View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.usernameFavorite);
            image = itemView.findViewById(R.id.imgFavorite);
        }
    }
}
