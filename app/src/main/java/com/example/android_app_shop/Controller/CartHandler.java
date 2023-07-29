package com.example.android_app_shop.Controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.android_app_shop.Model.Cart;
import com.example.android_app_shop.Model.ImageProduct;
import com.example.android_app_shop.Model.Product;

import java.util.ArrayList;
import java.util.List;

public class CartHandler extends SQLiteOpenHelper {
    public static final String DB_NAME = "smartphone.db";
    private static final int DB_VERSION = 1;
//    private static final String TABLE_NAME = "IMAGE_DETAIL_PRODUCT";
private static final String TABLE_NAME = "PRODUCT";
    private static final String ID_COL = "ID";
    private static final String ID_PRODUCT_COL = "ID_Product";
    private static final String URL_COL = "URL_Image";

    private static final String NAME_COL = "NameProduct";

    private static final String PRICE_COL = "Price";
    public static String path = "/data/data/com.example.android_app_shop/database/smartphone.db";


    public CartHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    @SuppressLint("Range")
    public Cart getProductById(int productId) {
        SQLiteDatabase db;
        Cart cart = null;
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COL + " = " + productId, null);

        if (cursor != null && cursor.moveToFirst()) {
            cart = new Cart();
            cart.setProductName(cursor.getString(cursor.getColumnIndex(NAME_COL)));
            cart.setProductPrice((double) cursor.getDouble(cursor.getColumnIndex(PRICE_COL)));
            cursor.close();
        }
        db.close();
        return cart;
    }

//    public List<ImageProduct> getListImagesByProductId(int productId) {
//        SQLiteDatabase db;
//        List<ImageProduct> listImages = new ArrayList<>();
//        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.CREATE_IF_NECESSARY);
//        Cursor cursor = db.rawQuery("SELECT * FROM IMAGE_DETAIL_PRODUCT WHERE " + ID_PRODUCT_COL + " = " + productId, null);
//        cursor.moveToFirst();
//        if (cursor != null) {
//            while (!cursor.isAfterLast()) {
//                @SuppressLint("Range") int imageId = cursor.getInt(cursor.getColumnIndex(ID_COL));
//                @SuppressLint("Range") String imageUrl = cursor.getString(cursor.getColumnIndex(URL_COL));
//                listImages.add(new ImageProduct(imageId, productId, imageUrl));
//                cursor.moveToNext();
//            }
//            cursor.close();
//        }
//        db.close();
//        return listImages;
//    }


}
