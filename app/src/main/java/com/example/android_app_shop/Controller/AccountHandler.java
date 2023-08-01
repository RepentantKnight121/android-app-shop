package com.example.android_app_shop.Controller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import androidx.annotation.Nullable;


public class AccountHandler extends SQLiteOpenHelper {
    Context context ;
    private static final String ID_COL = "ID";
    private static final String TABLE_NAME = "ACCOUNT";
    private static final String Username_COL = "Username";
    private static final String Password_COL = "Password";
    private static final String DisplayName_COL = "DisplayName";

    // Get the database manager
    DatabaseHelper databaseManager = DatabaseHelper.getInstance(context);

    // Get the database
    SQLiteDatabase database = databaseManager.getDatabase();

    public AccountHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                ID_COL + " INTEGER PRIMARY KEY, " +
                Username_COL + " TEXT, " +
                Password_COL + " TEXT, " +
                DisplayName_COL + " TEXT)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void ChangePassword(String username ,String newPassword ){
        // Create the update statement
        String query = "UPDATE account SET password = ? WHERE username = ?";

        // Compile the update statement
        SQLiteStatement statement = database.compileStatement(query);

        // Set the values for the parameters
        statement.bindString(1, newPassword);
        statement.bindString(2, username);

        int rowEffect = statement.executeUpdateDelete();
        Log.d("Cột ảnh hưởng" , String.valueOf(rowEffect));
    }

    public boolean AddAccount(String username, String password) {
        try {
            // Create a query
            String query = "INSERT OR IGNORE INTO account (username, password) VALUES (?, ?)";

            // Execute the query
            SQLiteStatement statement = database.compileStatement(query);
            statement.bindString(1, username);
            statement.bindString(2, password);
            statement.execute();
            return true ;
        }
        catch (Exception e){
            return false;
        }
    }

    public void initData(){
        AddAccount("admin001" , "123" );
        AddAccount("admin002" , "123" );
        AddAccount("admin003" , "123" );
        AddAccount("admin004" , "123" );
    }

    // Check if a user exists in the database
    public boolean checkUser(String username, String password) {

        try{
            Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + Username_COL + " = ? AND " + Password_COL + " = ?", new String[]{username, password});
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    cursor.close();
                    return true;
                }
                cursor.close();
            }
            return false;
        }catch (Exception e){
            throw e;
        }
    }
}
