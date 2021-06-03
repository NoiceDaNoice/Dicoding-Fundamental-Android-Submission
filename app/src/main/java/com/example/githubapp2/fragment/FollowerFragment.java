package com.example.githubapp2.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.githubapp2.api.iUser;
import com.example.githubapp2.R;
import com.example.githubapp2.adapter.AdapterFollower;
import com.example.githubapp2.model.FollowerModel;
import com.example.githubapp2.model.UserGithubModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

public class FollowerFragment extends Fragment {
    private RecyclerView rvFollower;
    private ProgressBar progressBarFollower;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_follower, container, false);
    }
    private void showProgress(Boolean state) {
        if (state) {
            progressBarFollower.setVisibility(View.VISIBLE);
        } else {
            progressBarFollower.setVisibility(View.GONE);
        }
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        progressBarFollower = (ProgressBar) view.findViewById(R.id.progressBar2);
        showProgress(true);
        super.onViewCreated(view, savedInstanceState);
        Intent intent = Objects.requireNonNull(getActivity()).getIntent();
        UserGithubModel userGithubModel = intent.getParcelableExtra("login");
        Log.d(TAG, "onViewCreated: "+userGithubModel.getLogin());

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.github.com").addConverterFactory(GsonConverterFactory.create()).build();
        rvFollower = view.findViewById(R.id.followerRv);
        rvFollower.setLayoutManager(new LinearLayoutManager(view.getContext()));

        iUser IUser = retrofit.create(iUser.class);
        Call<List<FollowerModel>> call = IUser.getFollower(userGithubModel.getLogin());
        call.enqueue(new Callback<List<FollowerModel>>() {
            @Override
            public void onResponse(@NotNull Call<List<FollowerModel>> call, @NotNull Response<List<FollowerModel>> response) {
                showProgress(false);
                ArrayList<FollowerModel> follower = new ArrayList<>(Objects.requireNonNull(response.body()));
                rvFollower.setAdapter(new AdapterFollower(getContext(),follower));
            }
            @Override
            public void onFailure(@NotNull Call<List<FollowerModel>> call, @NotNull Throwable t) {

            }
        });
    }
}