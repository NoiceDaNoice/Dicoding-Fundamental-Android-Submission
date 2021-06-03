package com.example.githubapp3;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.githubapp3.adapter.FragmentAdapter;
import com.example.githubapp3.api.iUser;
import com.example.githubapp3.db.FavUserHelper;
import com.example.githubapp3.model.DetailFavUserGithubModel;
import com.example.githubapp3.model.DetailUserGithubModel;
import com.example.githubapp3.model.UserGithubModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.githubapp3.db.DatabaseContract.UserFavoriteColumns.COMPANY;
import static com.example.githubapp3.db.DatabaseContract.UserFavoriteColumns.CONTENT_URI;
import static com.example.githubapp3.db.DatabaseContract.UserFavoriteColumns.FAVORITE;
import static com.example.githubapp3.db.DatabaseContract.UserFavoriteColumns.IMAGE;
import static com.example.githubapp3.db.DatabaseContract.UserFavoriteColumns.LOCATION;
import static com.example.githubapp3.db.DatabaseContract.UserFavoriteColumns.NAME;
import static com.example.githubapp3.db.DatabaseContract.UserFavoriteColumns.USERNAME;

public class DetailUserActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG ="TAG" ;
    int result = 0 ;
    private Uri uriWithUsername;
    TextView imageUrl,username,name,location,company;
    ImageView image;
    UserGithubModel userGithubModel;
    DetailUserGithubModel detailUser;
    DetailFavUserGithubModel detailUserFav;
    ProgressDialog progress;
    Button btn;
    String imageAvatar = "";
    private boolean isFavorite = false;
    private int position;
    private FavUserHelper favUserHelper;

    public static final String zero = "0";
    public static final String EXTRA_NOTE = "extra_note";
    public static final String EXTRA_POSITION = "extra_position";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_user);
        progress = ProgressDialog.show(this, "Loading", "Please wait.....", true);
        favUserHelper = FavUserHelper.getInstance(getApplicationContext());
        favUserHelper.open();
        Objects.requireNonNull(getSupportActionBar()).setTitle("Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btn = findViewById(R.id.buttonfav);

        userGithubModel = getIntent().getParcelableExtra("login");
        detailUserFav = getIntent().getParcelableExtra(EXTRA_NOTE);
        Log.d(TAG, "detailfavusergithubmodel: "+ detailUserFav);
        Log.d(TAG, "usergithubmodel: "+userGithubModel);

        if(userGithubModel!=null){
            Cursor cursor = favUserHelper.queryById(userGithubModel.getLogin());
            if(cursor.moveToNext()){
                setData();
                isFavorite = true;
                btn.setText("Unfavorite");
            }
        }

        if(detailUserFav!=null){
            setDataFav();
            isFavorite = true;
            btn.setText("Unfavorite");
        }else{
            setData();
        }

        btn.setOnClickListener(this);
    }

    public void setData(){

        Intent intent = getIntent();
        userGithubModel = intent.getParcelableExtra("login");
        imageUrl = findViewById(R.id.imageUrl);
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
                    imageUrl.setText(userGithubModel.getAvatar_url());
                    detailUser.setImage(userGithubModel.getAvatar_url());
                    name.setText(Objects.requireNonNull(detailUser).getName());
                    if(detailUser.getLocation()==null){
                        location.setText("Unknown");
                    }else{
                        location.setText(detailUser.getCompany());
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
                    Log.d("TAG", "show Data Main: "+ " " + detailUser.getUsername() + " " + detailUser.getImage());
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
        imageUrl = findViewById(R.id.imageUrl);
        username = findViewById(R.id.usernameDetail);
        image = findViewById(R.id.image);
        name = findViewById(R.id.nameDetail);
        location = findViewById(R.id.locationDetail);
        company = findViewById(R.id.companyDetail);
        Intent intent = getIntent();
        DetailFavUserGithubModel detailUserFav = intent.getParcelableExtra(EXTRA_NOTE);
        Log.d(TAG, "setDataFav: "+ detailUserFav.getUsername()+" "+detailUserFav.getName()+" "+detailUserFav.getImage()+" "+detailUserFav.getLocation()+" "+detailUserFav.getCompany());
        Glide.with(DetailUserActivity.this).load(detailUserFav.getImage()).into(image);
        imageUrl.setText(detailUserFav.getImage());
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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonfav) {
            if (isFavorite) {
                FavUserHelper.deleteById(username.getText().toString());
                Toast.makeText(this, "Delete Favorite", Toast.LENGTH_SHORT).show();
                btn.setText("Favorite");
                isFavorite = false;
            } else {
                ContentValues values = new ContentValues();
                String insertUsername = username.getText().toString();
                String insertName = name.getText().toString();
                String insertImage = imageUrl.getText().toString();
                String insertLocation = location.getText().toString();
                String insertCompany = company.getText().toString();
                values.put(USERNAME,insertUsername);
                values.put(NAME,insertName);
                values.put(IMAGE,insertImage);
                values.put(LOCATION,insertLocation);
                values.put(COMPANY,insertCompany);
                values.put(FAVORITE,zero);
                getContentResolver().insert(CONTENT_URI, values);

                Toast.makeText(this, "Favorite", Toast.LENGTH_SHORT).show();
                btn.setText("Unfavorite");
                isFavorite = true;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.about) {
            Intent intent = new Intent(DetailUserActivity.this, AboutActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.languague) {
            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intent);
        }else if (item.getItemId() == R.id.favorite) {
            Intent intent = new Intent(DetailUserActivity.this, FavoriteActivity.class);
            startActivity(intent);
        }else if (item.getItemId() == R.id.setting) {
            Intent intent = new Intent(DetailUserActivity.this, SettingActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}


