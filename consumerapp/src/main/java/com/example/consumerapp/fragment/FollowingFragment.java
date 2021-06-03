package com.example.consumerapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.consumerapp.R;
import com.example.consumerapp.adapter.AdapterFollowing;
import com.example.consumerapp.api.iUser;
import com.example.consumerapp.model.DetailFavUserGithubModel;
import com.example.consumerapp.model.FollowingModel;
import com.example.consumerapp.model.UserGithubModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.consumerapp.DetailUserActivity.EXTRA_NOTE;

public class FollowingFragment extends Fragment {
    private RecyclerView rvFollowing;
    private ProgressBar progressBarFollowing;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_following, container, false);
    }
    private void showProgress(Boolean state) {
        if (state) {
            progressBarFollowing.setVisibility(View.VISIBLE);
        } else {
            progressBarFollowing.setVisibility(View.GONE);
        }
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvFollowing = view.findViewById(R.id.followingRv);
        rvFollowing.setLayoutManager(new LinearLayoutManager(view.getContext()));
        progressBarFollowing= (ProgressBar) view.findViewById(R.id.progressBar3);
        showProgress(true);
        Intent intent = Objects.requireNonNull(getActivity()).getIntent();
        UserGithubModel userGithubModel = intent.getParcelableExtra("login");
        DetailFavUserGithubModel detailFavUserGithubModel = intent.getParcelableExtra(EXTRA_NOTE);
        if(detailFavUserGithubModel==null){
            getData(userGithubModel.getLogin());
        }else{
            getData(detailFavUserGithubModel.getUsername());
        }
    }

    void getData(String username){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.github.com").addConverterFactory(GsonConverterFactory.create()).build();
        iUser IUser = retrofit.create(iUser.class);
        Call<List<FollowingModel>> call = IUser.getFollowing(username);
        call.enqueue(new Callback<List<FollowingModel>>() {
            @Override
            public void onResponse(@NotNull Call<List<FollowingModel>> call, @NotNull Response<List<FollowingModel>> response) {
                showProgress(false);
                ArrayList<FollowingModel> following = new ArrayList<>(Objects.requireNonNull(response.body()));
                rvFollowing.setAdapter(new AdapterFollowing(getContext(),following));
            }
            @Override
            public void onFailure(@NotNull Call<List<FollowingModel>> call, @NotNull Throwable t) {

            }
        });
    }

}