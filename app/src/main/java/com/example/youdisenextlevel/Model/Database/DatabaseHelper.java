package com.example.youdisenextlevel.Model.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "youdiseNext.db";

    public static final String USERS_TABLE_NAME = "users";
    public static final String USERS_COL_ID = "userID";
    public static final String USERS_COL_USERNAME = "USERNAME";
    public static final String USERS_COL_EMAIL = "EMAIL";
    public static final String USERS_COL_PHONE = "PHONE";
    public static final String USERS_COL_PASSWORD = "PASSWORD";
    public static final String USERS_COL_COUNTRY = "COUNTRY";
    public static final String USERS_SQL_CREATE_TABLE = "CREATE TABLE " + USERS_TABLE_NAME + " (" +
            USERS_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            USERS_COL_USERNAME + " INTEGER, " +
            USERS_COL_EMAIL + " TEXT, " +
            USERS_COL_PHONE + " TEXT, " +
            USERS_COL_PASSWORD + " TEXT, " +
            USERS_COL_COUNTRY + " TEXT)";

    public static final String ADMINS_TABLE_NAME = "admins";
    public static final String ADMINS_COL_ID = "adminID";
    public static final String ADMINS_COL_FULLNAME = "FULLNAME";
    public static final String ADMINS_COL_EMAIL = "EMAIL";
    public static final String ADMINS_COL_PASSWORD = "PASSWORD";
    public static final String ADMINS_SQL_CREATE_TABLE = "CREATE TABLE " + ADMINS_TABLE_NAME + " (" +
            ADMINS_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ADMINS_COL_FULLNAME + " INTEGER, " +
            USERS_COL_EMAIL + " TEXT, " +
            ADMINS_COL_PASSWORD + " TEXT)";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        //SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL("CREATE TABLE " + USERS_TABLE_NAME + " (userID INTEGER PRIMARY KEY AUTOINCREMENT, "+ USERS_COL_USERNAME +" TEXT, "+ USERS_COL_EMAIL +" TEXT, "+ USERS_COL_PHONE +" TEXT, "+ USERS_COL_PASSWORD +" TEXT, "+ USERS_COL_COUNTRY +" TEXT)");
        db.execSQL(USERS_SQL_CREATE_TABLE);
        //db.execSQL("CREATE TABLE admins (adminID INTEGER PRIMARY KEY AUTOINCREMENT, "+ ADMINS_COL_FULLNAME +" TEXT, "+ ADMINS_COL_EMAIL +" TEXT, "+ ADMINS_COL_PASSWORD +" TEXT)");
        db.execSQL(ADMINS_SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ADMINS_TABLE_NAME);
        onCreate(db);
    }

    //inserting USER in database
    public boolean addUser(String name, String email, String phone, String password, String coutry) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USERS_COL_USERNAME, name);
        contentValues.put(USERS_COL_EMAIL, email);
        contentValues.put(USERS_COL_PHONE, phone);
        contentValues.put(USERS_COL_PASSWORD, password);
        contentValues.put(USERS_COL_COUNTRY, coutry);
        long result = db.insert(USERS_TABLE_NAME, null, contentValues);
        if (result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    //inserting ADMIN in database
    public boolean addAdmin(String name, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ADMINS_COL_FULLNAME, name);
        contentValues.put(ADMINS_COL_EMAIL, email);
        contentValues.put(ADMINS_COL_PASSWORD, password);
        long result = db.insert(ADMINS_TABLE_NAME, null, contentValues);
        if (result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    //check if email exist
    public boolean checkMailUser(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE EMAIL=?", new String[]{email}); //=? condition
        if (cursor.getCount() > 0){
            return true;
        }
        else{
            return false;
        }
    }

    //check if email exist =
    public boolean checkMailAdmin(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM admins WHERE EMAIL=?", new String[]{email}); //=? condition
        if (cursor.getCount() > 0){
            return true;
        }
        else{
            return false;
        }
    }

    //authentification
    public boolean checkUser(String email, String password){
        String [] columns = {USERS_COL_ID};
        SQLiteDatabase db = getReadableDatabase();
        String selection = USERS_COL_EMAIL + "=?" + " and " + USERS_COL_PASSWORD + "=?";
        String [] selectionArgs = {email, password};
        Cursor cursor = db.query(USERS_TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if (count > 0){
            return true;
        }
        else{
            return false;
        }
    }

    //authentification
    public boolean checkAdmin(String email, String password){
        String [] columns = {ADMINS_COL_ID};
        SQLiteDatabase db = getReadableDatabase();
        String selection = ADMINS_COL_EMAIL + "=?" + " and " + ADMINS_COL_PASSWORD + "=?";
        String [] selectionArgs = {email, password};
        Cursor cursor = db.query(ADMINS_TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if (count > 0){
            return true;
        }
        else{
            return false;
        }
    }

}
