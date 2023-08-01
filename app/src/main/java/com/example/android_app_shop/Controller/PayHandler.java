package com.example.android_app_shop.Controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.android_app_shop.Model.Pay;

public class PayHandler extends SQLiteOpenHelper {
    public static final String DB_NAME = "smartphone.db";
    private static final int DB_VERSION = 1;
    //    private static final String TABLE_NAME = "IMAGE_DETAIL_PRODUCT";
    private static final String TABLE_NAME = "PRODUCT";
    private static final String ID_COL = "ID";
    private static final String ID_PRODUCT_COL = "ID_Product";
    private static final String URL_COL = "URL_Image";

    private static final String NAME_COL = "NameProduct";

    private static final String PRICE_COL = "Price";


    private static final String STRORAGE_COL = "Strorage";
    private static final String COLOR_COL = "Color";
    public static String path = "/data/data/com.example.android_app_shop/database/smartphone.db";


    public PayHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    @SuppressLint("Range")
    public Pay getProductById(int productId) {
        SQLiteDatabase db;
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COL + " = " + productId, null);

        Pay pay = null;
        if (cursor.moveToFirst()) {
            pay = new Pay();
            pay.setId(cursor.getInt(cursor.getColumnIndex(ID_COL)));
            pay.setProductName(cursor.getString(cursor.getColumnIndex(NAME_COL)));
            pay.setProductPrice(cursor.getDouble(cursor.getColumnIndex(PRICE_COL)));
            pay.setColor(cursor.getString(cursor.getColumnIndex(COLOR_COL)));
            pay.setStorage(cursor.getString(cursor.getColumnIndex(STRORAGE_COL)));
        }
        cursor.close();
        db.close();
        return pay;
    }
}
