package com.example.githubapp2;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.githubapp2.api.iUser;
import com.example.githubapp2.adapter.FragmentAdapter;
import com.example.githubapp2.model.DetailUserGithubModel;
import com.example.githubapp2.model.UserGithubModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailUser extends AppCompatActivity {
    DetailUserGithubModel detailUser;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        progress = ProgressDialog.show(this, "Loading", "Please wait.....", true);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_user);
        showData();
        showFragment();
    }

    public void showData(){

        Intent intent = getIntent();
        UserGithubModel userGithubModel = intent.getParcelableExtra("login");

        TextView username = findViewById(R.id.usernameDetail);
        ImageView img = findViewById(R.id.image);

        username.setText(userGithubModel.getLogin());
        Glide.with(DetailUser.this).load(userGithubModel.getAvatar_url()).into(img);
        showDataAPI(userGithubModel.getLogin());
    }

    public void showDataAPI(String username){
        TextView name = findViewById(R.id.nameDetail);
        TextView location = findViewById(R.id.locationDetail);
        TextView company = findViewById(R.id.companyDetail);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.github.com").addConverterFactory(GsonConverterFactory.create()).build();

        iUser IUser = retrofit.create(iUser.class);
        Call<DetailUserGithubModel> call = IUser.getDetail(username);
        call.enqueue(new Callback<DetailUserGithubModel>() {
            @Override
            public void onResponse(@NotNull Call<DetailUserGithubModel> call, @NotNull Response<DetailUserGithubModel> response) {
                if (response.isSuccessful()) {
                    detailUser = response.body();
                    name.setText(Objects.requireNonNull(detailUser).getName());
                    if(detailUser.getLocation()==null){
                        location.setText("Unknown");
                    }else{
                        location.setText(detailUser.getCompany());
                    }

                    if(detailUser.getCompany()==null){
                        company.setText("Unknown");
                    }else{
                        company.setText(detailUser.getCompany());
                    }
                    Log.d("TAG", "onResponse: "+ detailUser);
                    progress.dismiss();
                } else {
                    Log.d("TAG", "onResponse: "+ detailUser);
                }
            }

            @Override
            public void onFailure(@NotNull Call<DetailUserGithubModel> call, @NotNull Throwable t) {

            }
        });
    }
    public void showFragment(){
        @StringRes
        final int[] TAB_TITLES = new int[]{
                R.string.tab_text_1,
                R.string.tab_text_2,

        };
        FragmentAdapter fragmentAdapter =  new FragmentAdapter(this);
        ViewPager2 viewPager = findViewById(R.id.vp);
        viewPager.setAdapter(fragmentAdapter);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(getResources().getString(TAB_TITLES[position]))
        ).attach();
        if(getSupportActionBar() != null) {
            getSupportActionBar().setElevation(0);
        }
    }
}