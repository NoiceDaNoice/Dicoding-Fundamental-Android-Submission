package com.example.githubapp3.helper;

import android.database.Cursor;

import com.example.githubapp3.db.DatabaseContract;
import com.example.githubapp3.model.DetailFavUserGithubModel;

import java.util.ArrayList;

public class MappingHelper {

    public static ArrayList<DetailFavUserGithubModel> mapCursorToArrayList(Cursor notesCursor) {
        ArrayList<DetailFavUserGithubModel> detailFavUser = new ArrayList<>();

        while (notesCursor.moveToNext()) {

            String username = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.UserFavoriteColumns.USERNAME));
            String name = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.UserFavoriteColumns.NAME));
            String image = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.UserFavoriteColumns.IMAGE));
            String location = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.UserFavoriteColumns.LOCATION));
            String company = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.UserFavoriteColumns.COMPANY));
            String favorite = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.UserFavoriteColumns.FAVORITE));

            detailFavUser.add(new DetailFavUserGithubModel(username,name,image,location,company,favorite));
        }

        return detailFavUser;
    }

    public static DetailFavUserGithubModel mapCursorToObject(Cursor notesCursor) {
        notesCursor.moveToFirst();
        String username = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.UserFavoriteColumns.USERNAME));
        String name = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.UserFavoriteColumns.NAME));
        String image = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.UserFavoriteColumns.IMAGE));
        String location = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.UserFavoriteColumns.LOCATION));
        String company = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.UserFavoriteColumns.COMPANY));
        String favorite = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.UserFavoriteColumns.FAVORITE));

        return new DetailFavUserGithubModel(username,name,image,location,company,favorite);
    }
}
