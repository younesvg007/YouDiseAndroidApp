package com.example.youdisenextlevel.Model.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.youdisenextlevel.Application.Myapplication;

public class YDDatabaseAdapter {
    private SQLiteDatabase sqLiteDB;

    public YDDatabaseAdapter(Context context){
        YoudiseDataBaseHelper mySQLiteOpenHelper = new YoudiseDataBaseHelper(context);
        sqLiteDB = mySQLiteOpenHelper.getWritableDatabase();

        if (Myapplication.isFirstLaunch()) {
            Myapplication.setFirstLaunch();
        }
    }

    //inserting USER in database
    public boolean insertUser(String name, String email, String password, String phone, String country) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(YouDise.USERS_COL_USERNAME, name);
        contentValues.put(YouDise.USERS_COL_EMAIL, email);
        contentValues.put(YouDise.USERS_COL_PASSWORD, password);
        contentValues.put(YouDise.USERS_COL_PHONE, phone);
        contentValues.put(YouDise.USERS_COL_COUNTRY, country);

        long resultId = sqLiteDB.insert(YouDise.USERS_TABLE_NAME, null, contentValues);
        return resultId != -1;
    }

    //authentification
    public boolean checkUser(String email, String password){
        String[] columns = {
                YouDise.USERS_COL_ID
        };
        //sqLiteDB = this.getReadableDatabase();
        // selection criteria
        String selection = YouDise.USERS_COL_EMAIL + " = ?" + " AND " + YouDise.USERS_COL_PASSWORD + " = ?";

        // selection arguments
        String[] selectionArgs = {email, password};

        // query user table with conditions

        Cursor cursor = sqLiteDB.query(YouDise.USERS_TABLE_NAME, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        //cursor.close();
        //sqLiteDB.close();
        if (cursorCount > 0) {
            return true;
        }
        else{
            return false;
        }
    }

    //check if email exist
    public boolean checkMailUser(String email){
        //SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = sqLiteDB.rawQuery("SELECT * FROM "+ YouDise.USERS_TABLE_NAME+" WHERE "+YouDise.USERS_COL_EMAIL + "= ?", new String[]{email}); //=? condition
        if (cursor.getCount() > 0){
            //cursor.moveToNext();
            return true;
        }
        else{
            return false;
        }
    }

    //inserting ADMIN in database
    public boolean insertAdmin(String name, String email, String password) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(YouDise.ADMINS_COL_FULLNAME, name);
        contentValues.put(YouDise.ADMINS_COL_EMAIL, email);
        contentValues.put(YouDise.ADMINS_COL_PASSWORD, password);

        long resultId = sqLiteDB.insert(YouDise.ADMINS_TABLE_NAME, null, contentValues);
        return resultId != -1;
    }

    public boolean checkAdmin(String email, String password) {
        String[] columns = {
                YouDise.ADMINS_COL_ID
        };
        //sqLiteDB = this.getReadableDatabase();
        // selection criteria
        String selection = YouDise.ADMINS_COL_EMAIL + " = ?" + " AND " + YouDise.ADMINS_COL_PASSWORD + " = ?";

        // selection arguments
        String[] selectionArgs = {email, password};

        // query user table with conditions

        Cursor cursor = sqLiteDB.query(YouDise.ADMINS_TABLE_NAME, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        //cursor.close();
        //sqLiteDB.close();
        if (cursorCount > 0) {
            return true;
        }
        else{
            return false;
        }
    }

    //check if email exist
    public boolean checkMailAdmin(String email){
        //SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = sqLiteDB.rawQuery("SELECT * FROM "+ YouDise.ADMINS_TABLE_NAME+" WHERE "+YouDise.ADMINS_COL_EMAIL + "= ?", new String[]{email}); //=? condition
        if (cursor.getCount() > 0){
            //cursor.moveToNext();
            return true;
        }
        else{
            return false;
        }
    }

    //inserting PRODUCT in database
    public boolean insertProduct(String name, String category, String description, int price, String dateTime, String imagePath) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(YouDise.PRODUCTS_COL_NAME, name);
        contentValues.put(YouDise.PRODUCTS_COL_CATEGORY, category);
        contentValues.put(YouDise.PRODUCTS_COL_DESCRIPTION, description);
        contentValues.put(YouDise.PRODUCTS_COL_PRICE, price);
        contentValues.put(YouDise.PRODUCTS_COL_DATETIME, dateTime);
        contentValues.put(YouDise.PRODUCTS_COL_IMAGE, imagePath);

        long resultId = sqLiteDB.insert(YouDise.PRODUCTS_TABLE_NAME, null, contentValues);
        return resultId != -1;
    }

    public Cursor getAllProduct(){
        Cursor cursor = sqLiteDB.rawQuery("SELECT * FROM "+YouDise.PRODUCTS_TABLE_NAME, null);
        return cursor;
    }
}
