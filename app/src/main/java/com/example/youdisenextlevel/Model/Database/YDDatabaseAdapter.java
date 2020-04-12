package com.example.youdisenextlevel.Model.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.youdisenextlevel.Application.Myapplication;
import com.example.youdisenextlevel.Model.Users;

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

    public Cursor getDataOfUser(String email){
        Cursor cursor = sqLiteDB.rawQuery("SELECT * FROM "+ YouDise.USERS_TABLE_NAME+" WHERE "+YouDise.USERS_COL_EMAIL + "= ?", new String[]{email});
        return cursor;
    }

    public boolean updateUser(String name, String newEmail, String password, String oldEmail) {

        String sql = "UPDATE " + YouDise.USERS_TABLE_NAME + " SET " + YouDise.USERS_COL_USERNAME + " = ?, " + YouDise.USERS_COL_EMAIL + " = ?, " + YouDise.USERS_COL_PASSWORD + " = ? WHERE "+ YouDise.USERS_COL_EMAIL+" = ?";
        sqLiteDB.execSQL(sql, new String[]{name, newEmail, password, oldEmail});

        return true ;
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

    public Cursor getDataOfProduct(String id){
        Cursor cursor = sqLiteDB.rawQuery("SELECT * FROM "+ YouDise.PRODUCTS_TABLE_NAME+" WHERE "+YouDise.PRODUCTS_COL_ID + "= ?", new String[]{id});
        return cursor;
    }

    //inserting CART in database
    public boolean insertCart(String name, String category, String imageUrl, int quantity, int price, int idProduct, int idUser) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(YouDise.CART_COL_NAME, name);
        contentValues.put(YouDise.CART_COL_CATEGORY, category);
        contentValues.put(YouDise.CART_COL_IMAGE, imageUrl);
        contentValues.put(YouDise.CART_COL_QUANTITY, quantity);
        contentValues.put(YouDise.CART_COL_PRICE, price);
        contentValues.put(YouDise.CART_COL_IDPRODUCT, idProduct);
        contentValues.put(YouDise.CART_COL_IDUSER, idUser);

        long resultId = sqLiteDB.insert(YouDise.CART_TABLE_NAME, null, contentValues);
        return resultId != -1;
    }

    public boolean checkProductUser(String idProduct, String idUser){
        String[] columns = {
                YouDise.CART_COL_ID
        };
        //sqLiteDB = this.getReadableDatabase();
        // selection criteria
        String selection = YouDise.CART_COL_IDPRODUCT + " = ?" + " AND " + YouDise.CART_COL_IDUSER + " = ?";

        // selection arguments
        String[] selectionArgs = {idProduct, idUser};

        // query user table with conditions

        Cursor cursor = sqLiteDB.query(YouDise.CART_TABLE_NAME, //Table to query
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

    public Cursor getAllCart(){
        Cursor cursor = sqLiteDB.rawQuery("SELECT * FROM "+YouDise.CART_TABLE_NAME, null);
        return cursor;
    }

    public Cursor getCartOfUser(String idUser){
        Cursor cursor = sqLiteDB.rawQuery("SELECT * FROM "+ YouDise.CART_TABLE_NAME+" WHERE "+YouDise.CART_COL_IDUSER + "= ?", new String[]{idUser});
        return cursor;
    }

    public Integer deleteSingleCart(String idCart){
        Integer rowDeleted = sqLiteDB.delete(YouDise.CART_TABLE_NAME, YouDise.CART_COL_ID + "= ?",new String[]{idCart});
        return rowDeleted;
    };

    public Integer deleteAllCartOfUser(String idUser){
        Integer rowDeleted = sqLiteDB.delete(YouDise.CART_TABLE_NAME, YouDise.CART_COL_IDUSER + "= ?",new String[]{idUser});
        return rowDeleted;
    };

    //inserting ORDER in database
    public boolean insertOrder(String name, String email, String adress, String country, String cardBank, String totalAmount, String dateTime, int idUser) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(YouDise.CHECKOUT_COL_NAME, name);
        contentValues.put(YouDise.CHECKOUT_COL_EMAIL, email);
        contentValues.put(YouDise.CHECKOUT_COL_ADRESS, adress);
        contentValues.put(YouDise.CHECKOUT_COL_COUNTRY, country);
        contentValues.put(YouDise.CHECKOUT_COL_CARDBANK, cardBank);
        contentValues.put(YouDise.CHECKOUT_COL_TOTALAMOUNT, totalAmount);
        contentValues.put(YouDise.CHECKOUT_COL_DATETIME, dateTime);
        contentValues.put(YouDise.CHECKOUT_COL_IDUSER, idUser);

        long resultId = sqLiteDB.insert(YouDise.CHECKOUT_TABLE_NAME, null, contentValues);
        return resultId != -1;
    }
}
