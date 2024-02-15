package com.rizkyfachrieza.inventoryapps.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preferences {
    static final String KEY_NAMA ="nama";
    static final String KEY_USERNAME = "username";
    static final String KEY_STATUS = "status";

    public static SharedPreferences getSharedPreference(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setUserLogin (Context context, boolean status, String nama, String username){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putBoolean(KEY_STATUS,status);
        editor.putString(KEY_NAMA, nama);
        editor.putString(KEY_USERNAME, username);
        editor.apply();
    }

    public static String getRegisteredUser(Context context){
        return getSharedPreference(context).getString(KEY_USERNAME,"");
    }

    public static String getLoggedInUser(Context context){
        return getSharedPreference(context).getString(KEY_NAMA,"");
    }

    public static boolean getLoggedInStatus(Context context){
        return getSharedPreference(context).getBoolean(KEY_STATUS,false);
    }

    public static void clearLoggedInUser (Context context){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.remove(KEY_USERNAME);
        editor.remove(KEY_STATUS);
        editor.remove(KEY_NAMA);
        editor.apply();
    }
}
