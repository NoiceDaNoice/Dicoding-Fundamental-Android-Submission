package com.example.githubapp3.db;

import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    public static final String AUTHORITY = "com.example.githubapp3";
    private static final String SCHEME = "content";

    public static final String TABLE_NAME = "user_favorite";
    public static final class UserFavoriteColumns implements BaseColumns {
        public static final String USERNAME = "username";
        public static final String NAME = "name";
        public static final String IMAGE = "image";
        public static final String LOCATION = "location";
        public static final String COMPANY = "company";
        public static final String FAVORITE = "favorite";

        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();
    }

}
