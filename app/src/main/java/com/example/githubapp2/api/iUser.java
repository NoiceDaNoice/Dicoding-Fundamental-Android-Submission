package com.example.githubapp2.api;

import com.example.githubapp2.model.DetailUserGithubModel;
import com.example.githubapp2.model.FollowerModel;
import com.example.githubapp2.model.FollowingModel;
import com.example.githubapp2.model.JSONRespone;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface iUser {
    @GET("/search/users")
    @Headers("Authorization: token xxxxxxxxxxxxxx")
    Call<JSONRespone> getSearch(
            @Query("q") String username
    );

    @GET("/users/{username}")
    @Headers("Authorization: token xxxxxxxxxxxxxx")
    Call<DetailUserGithubModel> getDetail(
            @Path("username") String username
    );

    @GET("users/{username}/followers")
    @Headers("Authorization: token xxxxxxxxxxxxxx")
    Call<List<FollowerModel>> getFollower(
            @Path("username") String username
    );

    @GET("users/{username}/following")
    @Headers("Authorization: token xxxxxxxxxxxxxx")
    Call<List<FollowingModel>> getFollowing(
            @Path("username") String username
    );

}
