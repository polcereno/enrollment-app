package com.example.debug.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class UserUtil {

    public static int getUserId(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getInt("user_id", -1);
    }

    public static String getUserRole(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("role", "");
    }

    public static boolean isLoggedIn(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean("isLoggedIn", false);
    }
}
