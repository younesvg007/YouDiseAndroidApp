package com.example.youdisenextlevel.Model.Database;

import android.media.Image;
import android.provider.BaseColumns;

public class YouDise implements BaseColumns {

    //DATABASE NAME & VERSION
    public static final String DATABASE_NAME = "YouDiseDatabase.db";
    public static final int DATABASE_VERSION = 1;

    //Table USERS
    public static final String USERS_TABLE_NAME = "users";
    public static final String USERS_COL_ID = "userID";
    public static final String USERS_COL_USERNAME = "USERNAME";
    public static final String USERS_COL_EMAIL = "EMAIL";
    public static final String USERS_COL_PHONE = "PHONE";
    public static final String USERS_COL_PASSWORD = "PASSWORD";
    public static final String USERS_COL_COUNTRY = "COUNTRY";
    public static final String USERS_SQL_CREATE_TABLE = "CREATE TABLE " + USERS_TABLE_NAME + " (" +
            USERS_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            USERS_COL_USERNAME + " TEXT, " +
            USERS_COL_EMAIL + " TEXT UNIQUE, " +
            USERS_COL_PASSWORD + " TEXT, " +
            USERS_COL_PHONE + " TEXT, " +
            USERS_COL_COUNTRY + " TEXT)";
    public static final String USERS_SQL_DROP_TABLE = "DROP TABLE " + USERS_TABLE_NAME + ";";

    //Table ADMINS
    public static final String ADMINS_TABLE_NAME = "admins";
    public static final String ADMINS_COL_ID = "adminID";
    public static final String ADMINS_COL_FULLNAME = "FULLNAME";
    public static final String ADMINS_COL_EMAIL = "EMAIL";
    public static final String ADMINS_COL_PASSWORD = "PASSWORD";
    public static final String ADMINS_SQL_CREATE_TABLE = "CREATE TABLE " + ADMINS_TABLE_NAME + " (" +
            ADMINS_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ADMINS_COL_FULLNAME + " TEXT, " +
            ADMINS_COL_EMAIL + " TEXT UNIQUE, " +
            ADMINS_COL_PASSWORD + " TEXT)";
    public static final String ADMINS_SQL_DROP_TABLE = "DROP TABLE " + ADMINS_TABLE_NAME + ";";

    //Table PRODUCTS
    public static final String PRODUCTS_TABLE_NAME = "products";
    public static final String PRODUCTS_COL_ID = "productID";
    public static final String PRODUCTS_COL_NAME = "NAME";
    public static final String PRODUCTS_COL_CATEGORY = "CATEGORY";
    public static final String PRODUCTS_COL_DESCRIPTION = "DESCRIPTION";
    public static final String PRODUCTS_COL_PRICE = "PRICE";
    public static final String PRODUCTS_COL_DATETIME = "DATETIME";
    public static final String PRODUCTS_COL_IMAGE = "IMAGE";
    public static final String PRODUCTS_SQL_CREATE_TABLE = "CREATE TABLE " + PRODUCTS_TABLE_NAME + " (" +
            PRODUCTS_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            PRODUCTS_COL_NAME + " TEXT, " +
            PRODUCTS_COL_CATEGORY + " TEXT, " +
            PRODUCTS_COL_DESCRIPTION + " TEXT, " +
            PRODUCTS_COL_PRICE + " INTEGER, " +
            PRODUCTS_COL_DATETIME + " TEXT, " +
            PRODUCTS_COL_IMAGE + " TEXT)";
    public static final String PRODUCTS_SQL_DROP_TABLE = "DROP TABLE " + PRODUCTS_TABLE_NAME + ";";

    //Table CART
    public static final String CART_TABLE_NAME = "carts";
    public static final String CART_COL_ID = "cartID";
    public static final String CART_COL_NAME = "NAME";
    public static final String CART_COL_CATEGORY = "CATEGORY";
    public static final String CART_COL_QUANTITY = "QUANTITY";
    public static final String CART_COL_PRICE = "PRICE";
    public static final String CART_COL_IMAGE = "IMAGE";
    public static final String CART_COL_IDPRODUCT = "productID";
    public static final String CART_COL_IDUSER = "userID";
    public static final String CART_SQL_CREATE_TABLE = "CREATE TABLE " + CART_TABLE_NAME + " (" +
            CART_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            CART_COL_NAME + " TEXT, " +
            CART_COL_CATEGORY + " TEXT, " +
            CART_COL_IMAGE + " TEXT, " +
            CART_COL_QUANTITY + " INTEGER, " +
            CART_COL_PRICE + " INTEGER, " +
            CART_COL_IDPRODUCT + " INTEGER, " +
            CART_COL_IDUSER + " INTEGER)";
    public static final String CART_SQL_DROP_TABLE = "DROP TABLE " + CART_TABLE_NAME + ";";

    //Table ORDER
    public static final String CHECKOUT_TABLE_NAME = "checkout";
    public static final String CHECKOUT_COL_ID = "checkoutID";
    public static final String CHECKOUT_COL_NAME = "NAME";
    public static final String CHECKOUT_COL_EMAIL = "EMAIL";
    public static final String CHECKOUT_COL_ADRESS = "ADRESS";
    public static final String CHECKOUT_COL_COUNTRY = "COUNTRY";
    public static final String CHECKOUT_COL_CARDBANK = "CARDBANK";
    public static final String CHECKOUT_COL_TOTALAMOUNT = "TOTALAMOUNT";
    public static final String CHECKOUT_COL_DATETIME= "DATETIME";
    public static final String CHECKOUT_COL_IDUSER = "userID";
    public static final String CHECKOUT_SQL_CREATE_TABLE = "CREATE TABLE " + CHECKOUT_TABLE_NAME + " (" +
            CHECKOUT_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            CHECKOUT_COL_NAME + " TEXT, " +
            CHECKOUT_COL_EMAIL + " TEXT, " +
            CHECKOUT_COL_ADRESS + " TEXT, " +
            CHECKOUT_COL_COUNTRY + " TEXT, " +
            CHECKOUT_COL_CARDBANK + " TEXT, " +
            CHECKOUT_COL_TOTALAMOUNT + " TEXT, " +
            CHECKOUT_COL_DATETIME + " TEXT, " +
            CHECKOUT_COL_IDUSER + " INTEGER)";
    public static final String CHECKOUT_SQL_DROP_TABLE = "DROP TABLE " + CHECKOUT_TABLE_NAME + ";";
}
