package com.example.debug.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class UserUtil {
    // Method to get the current user's ID from SharedPreferences
    public static int getCurrentUserId(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getInt("user_id", -0); // Default -1 if user_id not found
    }
}
