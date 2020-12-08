package com.example.youdisenextlevel.Model.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class YoudiseDataBaseHelper extends SQLiteOpenHelper {

    //creation de la BASE DE DONNEES
    public YoudiseDataBaseHelper(Context context) {
        super(context, YouDise.DATABASE_NAME, null, YouDise.DATABASE_VERSION);
    }

    //creation des TABLES
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("MY_LOG", "Création de la base de données");
        db.execSQL(YouDise.USERS_SQL_CREATE_TABLE);
        db.execSQL(YouDise.ADMINS_SQL_CREATE_TABLE);
        db.execSQL(YouDise.PRODUCTS_SQL_CREATE_TABLE);
        db.execSQL(YouDise.CART_SQL_CREATE_TABLE);
        db.execSQL(YouDise.CHECKOUT_SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("MY_LOG", "Mise à jour de la base de données (DROP)");
        db.execSQL(YouDise.USERS_SQL_DROP_TABLE);
        db.execSQL(YouDise.ADMINS_SQL_DROP_TABLE);
        db.execSQL(YouDise.PRODUCTS_SQL_DROP_TABLE);
        db.execSQL(YouDise.CART_SQL_DROP_TABLE);
        db.execSQL(YouDise.CHECKOUT_SQL_DROP_TABLE);
        onCreate(db);
    }
}
