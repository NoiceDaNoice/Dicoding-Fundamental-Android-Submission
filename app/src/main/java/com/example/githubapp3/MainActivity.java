package com.example.githubapp3;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.githubapp3.adapter.AdapterMain;
import com.example.githubapp3.api.iUser;
import com.example.githubapp3.db.FavUserHelper;
import com.example.githubapp3.model.JSONRespone;
import com.example.githubapp3.model.UserGithubModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rv;
    List<UserGithubModel> userGithub = new ArrayList<>();
    private ProgressBar progressBar;
    private FavUserHelper favUserHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        favUserHelper = FavUserHelper.getInstance(getApplicationContext());
        favUserHelper.open();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Github");
        rv = findViewById(R.id.rv_main);
        progressBar = findViewById(R.id.progressBar);
        rv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        showProgress(false);
        search();
    }

    private void showProgress(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
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
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.languague) {
            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intent);
        }else if (item.getItemId() == R.id.favorite) {
            Intent intent = new Intent(MainActivity.this, FavoriteActivity.class);
            startActivity(intent);
        }else if (item.getItemId() == R.id.setting) {
            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void search(){
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Username");
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String name) {
                showProgress(true);
                if (name != null) {
                    getDataUsername(name);
                    Log.d("TAG", "onQueryTextSubmit: "+ name);
                } else {
                    Toast.makeText(MainActivity.this, "Username please!", Toast.LENGTH_SHORT).show();
                }
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return true;
            }
        });
    }

    public void getDataUsername(String username){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.github.com").addConverterFactory(GsonConverterFactory.create()).build();

        iUser IUser = retrofit.create(iUser.class);
        Call<JSONRespone> call = IUser.getSearch(username);

        call.enqueue(new Callback<JSONRespone>() {
            @Override
            public void onResponse(@NotNull Call<JSONRespone> call, @NotNull Response<JSONRespone> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    userGithub = response.body().getItems();
                    if(userGithub.size()==0){
                        Toast.makeText(MainActivity.this, "No Username", Toast.LENGTH_SHORT).show();
                    }else{
                        rv.setAdapter(new AdapterMain(MainActivity.this, userGithub));
                        Log.d("TAG", "onResponse: "+ userGithub +" "+ userGithub.size());
                    }
                    showProgress(false);
                } else {
                    assert response.body() != null;
                    Log.d("TAG", "onResponse: "+ response.body().getItems());
                }
            }

            @Override
            public void onFailure(@NotNull Call<JSONRespone> call, @NotNull Throwable t) {

            }
        });

    }
}