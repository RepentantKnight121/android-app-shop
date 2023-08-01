package com.example.android_app_shop.Controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.android_app_shop.Model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductHandlder extends SQLiteOpenHelper {
    public static final String DB_NAME = "SMARTPHONE.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "PRODUCT";
    private static final String ID_COL = "ID";
    private static final String NAME_COL = "NameProduct";
    private static final String COLOR_COL = "Color";
    private static final String STORAGE_COL = "Storage";
    private static final String PRICE_COL = "Price";
    private static final String ID_CATEGORY_COL = "ID_Brand";
    public static String path = "/data/data/com.example.android_app_shop/database/smartphone.db";

    SQLiteDatabase db;

    public ProductHandlder(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                ID_COL + " INTEGER PRIMARY KEY, " +
                NAME_COL + " TEXT, " +
                COLOR_COL + " TEXT, " +
                STORAGE_COL + " TEXT, " +
                PRICE_COL + " REAL, " +
                ID_CATEGORY_COL + " INTEGER, " +
                "FOREIGN KEY (" + ID_CATEGORY_COL + ") REFERENCES CATEGORY(ID)" +
                ")";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public ArrayList<Product> loadProduct(){
        SQLiteDatabase db;
        ArrayList<Product> lstProduct = new ArrayList<>();
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        Cursor cursor = db.rawQuery("SELECT * FROM Product", null);
        cursor.moveToFirst();
        do{
            Product product = new Product();
            product.setID(cursor.getInt(0));
            product.setNameProduct(cursor.getString(1));
            product.setColor(cursor.getString(2));
            product.setStorage(cursor.getInt(3));
            product.setPrice(cursor.getFloat(4));
            lstProduct.add(product);
        }while (cursor.moveToNext());
        cursor.close();
        db.close();
        return lstProduct;
    }

    public void initData(){
        SQLiteDatabase db;
        db= SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        onCreate(db);
        String row1 = "INSERT OR IGNORE INTO " + TABLE_NAME + " (" + ID_COL + ", " + NAME_COL + ", " + COLOR_COL + ", " + STORAGE_COL + ", " + PRICE_COL + ") VALUES (1, 'IPHONE 15', 'white', 256, 1150)";
        db.execSQL(row1);
        String row2 = "INSERT OR IGNORE INTO " + TABLE_NAME + " (" + ID_COL + ", " + NAME_COL + ", " + COLOR_COL + ", " + STORAGE_COL + ", " + PRICE_COL + " ) VALUES (2, 'IPHONE 14', 'yellow', 256, 150)";
        db.execSQL(row2);
        String row3 = "INSERT OR IGNORE INTO " + TABLE_NAME + " (" + ID_COL + ", " + NAME_COL + ", " + COLOR_COL + ", " + STORAGE_COL + ", " + PRICE_COL + ") VALUES (3, 'Iphone XS Max', 'black', 512, 200.000)";
        db.execSQL(row3);
    }

    @SuppressLint("Range")
    public Product getProductById(int productId) {
        SQLiteDatabase db;
        Product product = null;
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COL + " = " + productId, null);
        if (cursor != null && cursor.moveToFirst()) {
            product = new Product();
            product.setID(cursor.getInt(cursor.getColumnIndex(ID_COL)));
            product.setNameProduct(cursor.getString(cursor.getColumnIndex(NAME_COL)));
            product.setColor(cursor.getString(cursor.getColumnIndex(COLOR_COL)));
            product.setStorage(cursor.getInt(cursor.getColumnIndex(STORAGE_COL)));
            product.setPrice((float) cursor.getDouble(cursor.getColumnIndex(PRICE_COL)));
//            product.setID_Category(cursor.getString(cursor.getColumnIndex(ID_CATEGORY_COL)));
            cursor.close();
        }
        db.close();
        return product;
    }

    public void openDatabase() {
        db = getWritableDatabase();
    }

    public void closeDatabase() {
        if (db != null && db.isOpen()) {
            db.close();
        }
    }

    @SuppressLint("Range")
    public List<Product> searchProducts(String keyword) {
        List<Product> productList = new ArrayList<>();
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        Product product = null;
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + NAME_COL + " LIKE '%" + keyword + "%'";
        Cursor cursor = db.rawQuery(query, null);
        System.out.println(cursor);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    product = new Product();
                    product.setID(cursor.getInt(cursor.getColumnIndex(ID_COL)));
                    product.setNameProduct(cursor.getString(cursor.getColumnIndex(NAME_COL)));
                    product.setColor(cursor.getString(cursor.getColumnIndex(COLOR_COL)));
                    product.setStorage(cursor.getInt(cursor.getColumnIndex(STORAGE_COL)));
                    product.setPrice(cursor.getFloat(cursor.getColumnIndex(PRICE_COL)));
                    productList.add(product);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        db.close();
        return productList;
    }
}
