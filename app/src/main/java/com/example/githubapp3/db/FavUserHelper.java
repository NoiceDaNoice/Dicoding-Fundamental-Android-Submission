package com.example.githubapp3.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.githubapp3.db.DatabaseContract.TABLE_NAME;
import static com.example.githubapp3.db.DatabaseContract.UserFavoriteColumns.USERNAME;

public class FavUserHelper {

    private static final String DATABASE_TABLE = TABLE_NAME;
    private static DatabaseHelper dataBaseHelper;
    private static FavUserHelper INSTANCE;

    private static SQLiteDatabase database;

    public FavUserHelper(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }

    public static FavUserHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FavUserHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
    }

    public void close() {
        dataBaseHelper.close();

        if (database.isOpen())
            database.close();
    }

    public Cursor queryAll() {
        return database.query(DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                USERNAME + " DESC");
    }

    public Cursor queryById(String id) {
        return database.query(DATABASE_TABLE, null
                , USERNAME + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public long insert(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int update(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, USERNAME + " = ?", new String[]{id});
    }

    public static int deleteById(String id) {
        return database.delete(DATABASE_TABLE, USERNAME + " = ?", new String[]{id});
    }
}