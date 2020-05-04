package com.example.youdisenextlevel.Application;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import com.example.youdisenextlevel.Model.Database.YDDatabaseAdapter;

public class Myapplication extends Application {

    public static final String ID_CHANNEL = "1";
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

        createNotificationChannels();
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

    private void createNotificationChannels(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channelNotif = new NotificationChannel(
                    ID_CHANNEL,
                    "channel 1",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channelNotif.setDescription("Id Channel is 1");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channelNotif);
        }

    }
}
