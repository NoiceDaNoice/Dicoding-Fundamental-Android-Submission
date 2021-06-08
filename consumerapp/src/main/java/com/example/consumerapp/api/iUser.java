package com.example.consumerapp.api;

import com.example.consumerapp.model.DetailFavUserGithubModel;
import com.example.consumerapp.model.DetailUserGithubModel;
import com.example.consumerapp.model.FollowerModel;
import com.example.consumerapp.model.FollowingModel;
import com.example.consumerapp.model.JSONRespone;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface iUser {
    @GET("/search/users")
    @Headers("Authorization: token xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")
    Call<JSONRespone> getSearch(
            @Query("q") String username
    );

    @GET("/users/{username}")
    @Headers("Authorization: token xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")
    Call<DetailUserGithubModel> getDetail(
            @Path("username") String username
    );

    @GET("/users/{username}")
    @Headers("Authorization: token xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")
    Call<DetailFavUserGithubModel> getDetailFav(
            @Path("username") String username
    );

    @GET("users/{username}/followers")
    @Headers("Authorization: token xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")
    Call<List<FollowerModel>> getFollower(
            @Path("username") String username
    );

    @GET("users/{username}/following")
    @Headers("Authorization: token xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")
    Call<List<FollowingModel>> getFollowing(
            @Path("username") String username
    );

}
