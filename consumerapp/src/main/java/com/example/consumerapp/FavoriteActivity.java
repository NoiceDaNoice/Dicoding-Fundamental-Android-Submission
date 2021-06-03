package com.example.consumerapp;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.consumerapp.adapter.FavoriteAdapter;
import com.example.consumerapp.db.DatabaseContract;
import com.example.consumerapp.helper.MappingHelper;
import com.example.consumerapp.model.DetailFavUserGithubModel;
import com.google.android.material.snackbar.Snackbar;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FavoriteActivity extends AppCompatActivity implements LoadNotesCallback {
    private RecyclerView rvFavorite;
    private FavoriteAdapter favoriteAdapter;
//    private FavUserHelper favUserHelper;
    private static final String EXTRA_STATE = "EXTRA_STATE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        rvFavorite = findViewById(R.id.rvFavorite);
        rvFavorite.setLayoutManager(new LinearLayoutManager(this));
        favoriteAdapter = new FavoriteAdapter(this);
        rvFavorite.setAdapter(favoriteAdapter);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Favorite");

        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());

        DataObserver myObserver = new DataObserver(handler, this);
        getContentResolver().registerContentObserver(DatabaseContract.UserFavoriteColumns.CONTENT_URI, true, myObserver);

        if (savedInstanceState == null) {
            new LoadNoteAsync(this, this).execute();
        } else {
            ArrayList<DetailFavUserGithubModel> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                favoriteAdapter.setListNotes(list);
            }
        }
    }

    @Override
    public void preExecute() {

    }

    @Override
    public void postExecute(ArrayList<DetailFavUserGithubModel> detailFavUserGithubModels) {
        if (detailFavUserGithubModels.size() > 0) {
            favoriteAdapter.setListNotes(detailFavUserGithubModels);
        } else {
            favoriteAdapter.setListNotes(new ArrayList<>());
            showSnackbarMessage("No Data");
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private static class LoadNoteAsync {

        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadNotesCallback> weakCallback;

        private LoadNoteAsync(Context context, LoadNotesCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        void execute() {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());

            weakCallback.get().preExecute();
            executor.execute(() -> {
                Context context = weakContext.get();
                Cursor dataCursor = context.getContentResolver().query(DatabaseContract.UserFavoriteColumns.CONTENT_URI, null, null, null, null);
                ArrayList<DetailFavUserGithubModel> userData = MappingHelper.mapCursorToArrayList(dataCursor);

                handler.post(() -> weakCallback.get().postExecute(userData));
            });
        }
    }

    private void showSnackbarMessage(String message) {
        Snackbar.make(rvFavorite, message, Snackbar.LENGTH_SHORT).show();
    }

    public static class DataObserver extends ContentObserver {

        final Context context;

        public DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new LoadNoteAsync(context, (LoadNotesCallback) context).execute();

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
            Intent intent = new Intent(FavoriteActivity.this, AboutActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.languague) {
            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intent);
        }else if (item.getItemId() == R.id.favorite) {
            Toast.makeText(this, R.string.already_on_favorite, Toast.LENGTH_LONG).show();
        }else if (item.getItemId() == R.id.setting) {
            Intent intent = new Intent(FavoriteActivity.this, SettingActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}

interface LoadNotesCallback {
    void preExecute();
    void postExecute(ArrayList<DetailFavUserGithubModel> detailFavUserGithubModels);
}