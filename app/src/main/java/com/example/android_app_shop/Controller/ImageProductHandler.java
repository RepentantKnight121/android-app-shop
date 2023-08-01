package com.example.android_app_shop.Controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.android_app_shop.Model.ImageProduct;

import java.util.ArrayList;
import java.util.List;

public class ImageProductHandler extends SQLiteOpenHelper {
    public static final String DB_NAME = "smartphone.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "IMAGE_DETAIL_PRODUCT";
    private static final String ID_COL = "ID";
    private static final String ID_PRODUCT_COL = "ID_Product";
    private static final String URL_COL = "URL_Image";
    public static String path = "/data/data/com.example.android_app_shop/database/smartphone.db";

    public ImageProductHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                ID_COL + " INTEGER PRIMARY KEY, " +
                URL_COL + " TEXT, " +
                ID_PRODUCT_COL + " INTEGER, " +
                "FOREIGN KEY (" + ID_PRODUCT_COL + ") REFERENCES PRODUCT(ID)" +
                ")";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void initData(){
        SQLiteDatabase db;
        db= SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        onCreate(db);
        String row1 = "INSERT OR IGNORE INTO " + TABLE_NAME + " (" + ID_COL + ", " + URL_COL + ", " + ID_PRODUCT_COL + ") VALUES (1, 'https://i.ebayimg.com/images/g/164AAOSwdXtjOmfb/s-l1600.png', 2)";
        db.execSQL(row1);
        String row2 = "INSERT OR IGNORE INTO " + TABLE_NAME + " (" + ID_COL + ", " + URL_COL + ", " + ID_PRODUCT_COL + ") VALUES (2, 'https://store.storeimages.cdn-apple.com/4982/as-images.apple.com/is/refurb-iphone11-yellow-2019?wid=2000&hei=2000&fmt=jpeg&qlt=90&.v=1611169089000\"\"https://store.storeimages.cdn-apple.com/4982/as-images.apple.com/is/refurb-iphone11-yellow-2019?wid=2000&hei=2000&fmt=jpeg&qlt=90&.v=1611169089000', 2)";
        db.execSQL(row2);
        String row3 = "INSERT OR IGNORE INTO " + TABLE_NAME + " (" + ID_COL + ", " + URL_COL + ", " + ID_PRODUCT_COL + ") VALUES (3, 'https://didonghan.vn/pic/product/iphone-xs-max-64gb-quoc-te-xach-tay-gia-tot-nhat-zin-10_6022a3ee-dd11-4eb5-90eb-957b8d48b1ac.jpg', 3)";
        db.execSQL(row3);
        String row4 = "INSERT OR IGNORE INTO " + TABLE_NAME + " (" + ID_COL + ", " + URL_COL + ", " + ID_PRODUCT_COL + ") VALUES (4, 'https://smartviets.com/template/plugins/timthumb.php?src=/upload/image/anh%20cu/xs%20%C4%91en.jpg&w=770&h=770&q=80', 3)";
        db.execSQL(row4);
        String row5 = "INSERT OR IGNORE INTO " + TABLE_NAME + " (" + ID_COL + ", " + URL_COL + ", " + ID_PRODUCT_COL + ") VALUES (5, 'https://muabandienthoai24h.vn/storage/upload/images/iphone-xs-max-den-1-org.jpg', 3)";
        db.execSQL(row5);
        String row6 = "INSERT OR IGNORE INTO " + TABLE_NAME + " (" + ID_COL + ", " + URL_COL + ", " + ID_PRODUCT_COL + ") VALUES (6, 'https://specifications-pro.com/wp-content/uploads/2023/01/iPhone-15-2.jpg', 1)";
        db.execSQL(row6);
        String row7 = "INSERT OR IGNORE INTO " + TABLE_NAME + " (" + ID_COL + ", " + URL_COL + ", " + ID_PRODUCT_COL + ") VALUES (7, 'https://economictimes.indiatimes.com/thumb/msid-94341230,width-1200,height-900,resizemode-4,imgsize-16180/iphone-15.jpg?from=mdr', 1)";
        db.execSQL(row7);
        String row8 = "INSERT OR IGNORE INTO " + TABLE_NAME + " (" + ID_COL + ", " + URL_COL + ", " + ID_PRODUCT_COL + ") VALUES (8, 'https://i0.wp.com/www.smartprix.com/bytes/wp-content/uploads/2022/12/iphone-15-to-come-with-this-major-design-update-new-leaks-reveal-titanium-frame.webp?ssl=1', 1)";
        db.execSQL(row8);
    }

    public List<ImageProduct> getListImagesByProductId(int productId) {
        SQLiteDatabase db;
        List<ImageProduct> listImages = new ArrayList<>();
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        Cursor cursor = db.rawQuery("SELECT * FROM IMAGE_DETAIL_PRODUCT WHERE " + ID_PRODUCT_COL + " = " + productId, null);
        cursor.moveToFirst();
        if (cursor != null) {
            while (!cursor.isAfterLast()) {
                @SuppressLint("Range") int imageId = cursor.getInt(cursor.getColumnIndex(ID_COL));
                @SuppressLint("Range") String imageUrl = cursor.getString(cursor.getColumnIndex(URL_COL));
                listImages.add(new ImageProduct(imageId, productId, imageUrl));
                cursor.moveToNext();
            }
            cursor.close();
        }
        db.close();
        return listImages;
    }
}
