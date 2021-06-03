package com.example.consumerapp.db;

import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    public static final String AUTHORITY = "com.example.githubapp3";
    private static final String SCHEME = "content";

    public static String TABLE_NAME = "user_favorite";
    public static final class UserFavoriteColumns implements BaseColumns {
        public static String USERNAME = "username";
        public static String NAME = "name";
        public static String IMAGE = "image";
        public static String LOCATION = "location";
        public static String COMPANY = "company";
        public static String FAVORITE = "favorite";

        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();
    }

}
