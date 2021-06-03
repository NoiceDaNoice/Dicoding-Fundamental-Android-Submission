package com.example.consumerapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.consumerapp.adapter.FragmentAdapter;
import com.example.consumerapp.api.iUser;
import com.example.consumerapp.model.DetailFavUserGithubModel;
import com.example.consumerapp.model.DetailUserGithubModel;
import com.example.consumerapp.model.UserGithubModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailUserActivity extends AppCompatActivity {
    private static final String TAG ="TAG" ;
    int result =0;
    TextView username,name,location,company;
    ImageView image;
    DetailUserGithubModel detailUser;
    DetailFavUserGithubModel detailUserFav;
    ProgressDialog progress;
    Button btn;
    String ImageAvatar = "";
    private boolean isFavorite = false;
    private int position;

    public static final String zero = "0";
    public static final String EXTRA_NOTE = "extra_note";
    public static final String EXTRA_POSITION = "extra_position";
    public static final int REQUEST_ADD = 100;
    public static final int RESULT_ADD = 101;
    public static final int REQUEST_UPDATE = 200;
    public static final int RESULT_UPDATE = 201;
    public static final int RESULT_DELETE = 301;
    private final int ALERT_DIALOG_CLOSE = 10;
    private final int ALERT_DIALOG_DELETE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_user);
        progress = ProgressDialog.show(this, "Loading", "Please wait.....", true);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        detailUserFav = getIntent().getParcelableExtra(EXTRA_NOTE);
        Log.d("TAG", "detailfavusergithubmodel: "+ detailUserFav);

        if(detailUserFav!=null){
            setDataFav();
            isFavorite = true;
        }else{
            setData();
        }

    }

    public void setData(){

        Intent intent = getIntent();
        UserGithubModel userGithubModel = intent.getParcelableExtra("login");

        username = findViewById(R.id.usernameDetail);
        image = findViewById(R.id.image);
        name = findViewById(R.id.nameDetail);
        location = findViewById(R.id.locationDetail);
        company = findViewById(R.id.companyDetail);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.github.com").addConverterFactory(GsonConverterFactory.create()).build();

        iUser IUser = retrofit.create(iUser.class);
        Call<DetailUserGithubModel> call = IUser.getDetail(userGithubModel.getLogin());
        call.enqueue(new Callback<DetailUserGithubModel>() {
            @Override
            public void onResponse(@NotNull Call<DetailUserGithubModel> call, @NotNull Response<DetailUserGithubModel> response) {
                if (response.isSuccessful()) {
                    detailUser = response.body();
                    username.setText(userGithubModel.getLogin());
                    detailUser.setUsername(userGithubModel.getLogin());
                    Glide.with(DetailUserActivity.this).load(userGithubModel.getAvatar_url()).into(image);
                    ImageAvatar = userGithubModel.getAvatar_url();
                    detailUser.setImage(userGithubModel.getAvatar_url());
                    if(detailUser.getName()==null){
                        name.setText("Unknown");
                    }else{
                        name.setText(detailUser.getName());
                    }
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
                    Log.d("TAG", "show Data Main: "+ detailUser + " " + detailUser.getUsername() + " " + detailUser.getImage());
                    showFragment();
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


    void setDataFav(){
        username = findViewById(R.id.usernameDetail);
        image = findViewById(R.id.image);
        name = findViewById(R.id.nameDetail);
        location = findViewById(R.id.locationDetail);
        company = findViewById(R.id.companyDetail);
        Intent intent = getIntent();
        DetailFavUserGithubModel detailUserFav = intent.getParcelableExtra(EXTRA_NOTE);
        Log.d(TAG, "setDataFav: "+ detailUserFav.getUsername()+" "+detailUserFav.getName()+" "+detailUserFav.getImage()+" "+detailUserFav.getLocation()+" "+detailUserFav.getCompany());
        Glide.with(DetailUserActivity.this).load(detailUserFav.getImage()).into(image);
        username.setText(detailUserFav.getUsername());
        name.setText(detailUserFav.getName());
        location.setText(detailUserFav.getLocation());
        company.setText(detailUserFav.getCompany());
        showFragment();
        progress.dismiss();
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


