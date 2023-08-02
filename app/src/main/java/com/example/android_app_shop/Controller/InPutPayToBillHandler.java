package com.example.android_app_shop.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.android_app_shop.Model.Bill;

public class InPutPayToBillHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "smartphone.db";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "BILL";
    private static final String ID_COL = "ID";
    private static final String ID_ACCOUNT_COL = "ID_Account";
    private static final String FULLNAMECUSTOMER_COL = "FullNameCustomer";
    private static final String GMAIL_COL = "Gmail";
    private static final String PHONENUMBER_COL = "PhoneNumber";
    private static final String TIMEODER_COL = "TimeOrder";
    private static final String STATUS_COL = "Status";

    public static String path = "/data/data/com.example.android_app_shop/database/smartphone.db";

    public InPutPayToBillHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ID_ACCOUNT_COL + " INTEGER , " +
                FULLNAMECUSTOMER_COL + " TEXT NOT NULL, " +
                GMAIL_COL + " TEXT NOT NULL, " +
                PHONENUMBER_COL + " TEXT NOT NULL," +
                TIMEODER_COL + " TEXT, " +
                STATUS_COL + " INTEGER DEFAULT 1 CHECK(\"Status\" IN (1, 2, 3, 4)), " +
                "FOREIGN KEY (" + ID_ACCOUNT_COL + ") REFERENCES ACCOUNT(ID)" +
                ")";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
//        db.execSQL(dropTableQuery);
//        onCreate(db);
    }

    public void initData() {
        SQLiteDatabase db;
        db= SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        String row1 = "INSERT OR IGNORE INTO " + TABLE_NAME + " (" + ID_COL + ", " + ID_ACCOUNT_COL  + ", " + FULLNAMECUSTOMER_COL + ", " + GMAIL_COL + ", " + PHONENUMBER_COL + "," + TIMEODER_COL + "," + STATUS_COL + ") VALUES (2, 2, 'IPHONE 15', 'white', 'a','18/04/2003',2)";
        db.execSQL(row1);
        db.close();
    }

    public void addBill(Bill bill) {
        SQLiteDatabase db ;
        db= SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        ContentValues values = new ContentValues();
//        values.put(ID_COL, bill.getId());
        values.put(ID_ACCOUNT_COL, bill.getIdAccount());
        values.put(FULLNAMECUSTOMER_COL, bill.getFullnamecutomer());
        values.put(GMAIL_COL, bill.getGmail());
        values.put(PHONENUMBER_COL, bill.getPhoneNumber());
        values.put(TIMEODER_COL, bill.getTimeOder());
        values.put(STATUS_COL, bill.getStatus());
        long result = db.insert(TABLE_NAME, null, values);
        db.close();
        if (result != -1) {
            // Successful insertion
        } else {
            // Failed to insert
        }
    }
}
