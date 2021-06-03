package com.example.githubapp3.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.githubapp3.db.FavUserHelper;

import static com.example.githubapp3.db.DatabaseContract.AUTHORITY;
import static com.example.githubapp3.db.DatabaseContract.TABLE_NAME;
import static com.example.githubapp3.db.DatabaseContract.UserFavoriteColumns.CONTENT_URI;

public class FavoriteProvider extends ContentProvider {

    private static final int NOTE = 1;
    private static final int NOTE_ID = 2;
    private FavUserHelper favUserHelper;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        sUriMatcher.addURI(AUTHORITY, TABLE_NAME, NOTE);

        sUriMatcher.addURI(AUTHORITY, TABLE_NAME + "/#", NOTE_ID);
    }
    @Override
    public boolean onCreate() {
        favUserHelper = FavUserHelper.getInstance(getContext());
        favUserHelper.open();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case NOTE:
                cursor = favUserHelper.queryAll();
                break;
            case NOTE_ID:
                cursor = favUserHelper.queryById(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long added;
        if (sUriMatcher.match(uri) == NOTE) {
            added = favUserHelper.insert(values);
        } else {
            added = 0;
        }

        getContext().getContentResolver().notifyChange(CONTENT_URI, null);

        return Uri.parse(CONTENT_URI + "/" + added);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int deleted;
        if (sUriMatcher.match(uri) == NOTE_ID) {
            deleted = FavUserHelper.deleteById(uri.getLastPathSegment());
        } else {
            deleted = 0;
        }

        getContext().getContentResolver().notifyChange(CONTENT_URI, null);

        return deleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int updated;
        if (sUriMatcher.match(uri) == NOTE_ID) {
            updated = favUserHelper.update(uri.getLastPathSegment(), values);
        } else {
            updated = 0;
        }

        getContext().getContentResolver().notifyChange(CONTENT_URI, null);

        return updated;
    }
}
