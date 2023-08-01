package com.example.android_app_shop.Controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "SMARTPHONE";
    public static String DATABASE_PATH = "/data/data/com.example.android_app_shop/database/smartphone.db";
    private static final int DATABASE_VERSION = 1;
    private static DatabaseHelper INSTANCE;
    private SQLiteDatabase db;

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new DatabaseHelper(context);
        }
        return INSTANCE;
    }

    public void CheckConnect(){
        if (db != null) {
            Log.d("CheckConnect", "succed");
        } else {
            Log.d("CheckConnect", "failed");
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
      //  db.execSQL("CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, email TEXT, password TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        onCreate(db);
    }

    public SQLiteDatabase getDatabase() {
        if (db == null || !db.isOpen()) {
            db = SQLiteDatabase.openDatabase(DATABASE_PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        }
        return db;
    }
}