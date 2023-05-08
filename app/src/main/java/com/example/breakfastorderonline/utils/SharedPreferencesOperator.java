package com.example.breakfastorderonline.utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;


public class SharedPreferencesOperator {

    private static final String SP_FILE_NAME = "bfoo_app_pref";
    private static final String USER_ACCOUNT_KEY = "USER_ACCOUNT";
    private static final String USER_SIGNED_IN_KEY = "USER_SIGNED_IN";

    private Context context;
    private SharedPreferences sharedPreferences;

    public SharedPreferencesOperator(@NonNull Context context) {
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE);
    }

    public boolean checkUserSignedIn(String userAccount) {
        return sharedPreferences.getString(USER_ACCOUNT_KEY, "").equals(userAccount) &&
            sharedPreferences.getBoolean(USER_SIGNED_IN_KEY, false);
    }

    public void setUserSignedIn(String userAccount) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_ACCOUNT_KEY, userAccount);
        editor.putBoolean(USER_SIGNED_IN_KEY, true);
        editor.apply();
    }

    public String getSignedInUserAccount() {
        return sharedPreferences.getString(USER_ACCOUNT_KEY, "");
    }
}
