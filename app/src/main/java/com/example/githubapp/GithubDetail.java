package com.example.githubapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.githubapp.Github.GithubModel;

public class GithubDetail extends AppCompatActivity {
    public static final String ITEM_EXTRA = "item_extra";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Detail");

        GithubModel githubModel = getIntent().getParcelableExtra(ITEM_EXTRA);
        TextView name = findViewById(R.id.nameDetail);
        TextView username = findViewById(R.id.usernameDetail);
        ImageView foto = (ImageView)findViewById(R.id.image);
        TextView follower = findViewById(R.id.detailFollower);
        TextView following = findViewById(R.id.detailFollowing);
        TextView company = findViewById(R.id.companyDetail);
        TextView location = findViewById(R.id.locationDetail);
        TextView repository = findViewById(R.id.detailRepository);

        username.setText(githubModel.getUsername());
        name.setText(githubModel.getName());
        Glide.with(GithubDetail.this).load(githubModel.getImg()).into(foto);
        follower.setText(githubModel.getFollower());
        following.setText(githubModel.getFollowing());
        company.setText(githubModel.getCompany());
        location.setText(githubModel.getLocation());
        repository.setText(githubModel.getRepository());

    }
}