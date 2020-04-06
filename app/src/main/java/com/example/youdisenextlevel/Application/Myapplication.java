package com.example.youdisenextlevel.Application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.youdisenextlevel.Model.Database.YDDatabaseAdapter;

public class Myapplication extends Application {

    private static boolean firstLaunch;
    private static String sharedPreferencesKey;
    private static YDDatabaseAdapter ydDatabaseAdapter;
    private static SharedPreferences sharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        sharedPreferences = this.getSharedPreferences(
                "database_config", Context.MODE_PRIVATE);

        sharedPreferencesKey = "is_first_launch";
        firstLaunch = sharedPreferences.getBoolean(sharedPreferencesKey, true);

        ydDatabaseAdapter = new YDDatabaseAdapter(this);
    }


    public static YDDatabaseAdapter getYdDatabaseAdapter() {
        return ydDatabaseAdapter;
    }

    public static boolean isFirstLaunch() {
        return firstLaunch;
    }

    public static void setFirstLaunch() {
        sharedPreferences
                .edit()
                .putBoolean(sharedPreferencesKey, false)
                .apply();
    }

}
