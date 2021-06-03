package com.example.githubapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ImageButton;

import com.example.githubapp.Github.GithubData;
import com.example.githubapp.Github.GithubModel;
import com.example.githubapp.Recycler.GithubAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvGithub;

    private ArrayList<GithubModel> list = new ArrayList<>();
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvGithub = findViewById(R.id.rv_github);
        rvGithub.setHasFixedSize(true);

        list.addAll(GithubData.getListData());
        showRecyclerList();
    }

    private void showRecyclerList() {
        rvGithub.setLayoutManager(new LinearLayoutManager(this));
        GithubAdapter githubAdapter = new GithubAdapter(list);
        rvGithub.setAdapter(githubAdapter);

        githubAdapter.setOnItemClickCallback(new GithubAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(GithubModel data) {
                Intent intent = new Intent(MainActivity.this, GithubDetail.class);
                intent.putExtra(GithubDetail.ITEM_EXTRA, data);
                startActivity(intent);
            }
        });
    }
}