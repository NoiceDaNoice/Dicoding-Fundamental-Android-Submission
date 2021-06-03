package com.example.githubapp3.api;

import com.example.githubapp3.model.DetailFavUserGithubModel;
import com.example.githubapp3.model.DetailUserGithubModel;
import com.example.githubapp3.model.FollowerModel;
import com.example.githubapp3.model.FollowingModel;
import com.example.githubapp3.model.JSONRespone;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface iUser {
    @GET("/search/users")
    @Headers("Authorization: token ***************")
    Call<JSONRespone> getSearch(
            @Query("q") String username
    );

    @GET("/users/{username}")
    @Headers("Authorization: token ***************")
    Call<DetailUserGithubModel> getDetail(
            @Path("username") String username
    );

    @GET("/users/{username}")
    @Headers("Authorization: token ***************")
    Call<DetailFavUserGithubModel> getDetailFav(
            @Path("username") String username
    );

    @GET("users/{username}/followers")
    @Headers("Authorization: token ***************")
    Call<List<FollowerModel>> getFollower(
            @Path("username") String username
    );

    @GET("users/{username}/following")
    @Headers("Authorization: token ***************")
    Call<List<FollowingModel>> getFollowing(
            @Path("username") String username
    );

}
