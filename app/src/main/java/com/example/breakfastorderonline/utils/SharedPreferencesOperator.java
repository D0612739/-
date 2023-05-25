package com.example.breakfastorderonline.utils;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;


public class SharedPreferencesOperator {

    private static final String SP_FILE_NAME = "bfoo_app_pref";
    private static final String USER_ACCOUNT_KEY = "USER_ACCOUNT";
    private static final String USER_SIGNED_IN_KEY = "USER_SIGNED_IN";
    private static final String MAIN_ACTIVITY_RECREATE_FLAG_KEY = "MAIN_ACTIVITY_RECREATE_FLAG";

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

    public void clearSignedInUser() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(USER_ACCOUNT_KEY).remove(USER_SIGNED_IN_KEY).apply();
    }

    public String getSignedInUserAccount() {
        if (sharedPreferences.getBoolean(USER_SIGNED_IN_KEY, false)) {
            return sharedPreferences.getString(USER_ACCOUNT_KEY, "");
        }
        return "";
    }

    /**
     * 在需要MainActivity重載入時，在跳轉頁面之前或finish當前頁面之前呼叫此方法
     */
    public void setMainActivityRecreateFlag() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(MAIN_ACTIVITY_RECREATE_FLAG_KEY, true);
        editor.apply();
    }

    public void clearMainActivityRecreateFlag() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(MAIN_ACTIVITY_RECREATE_FLAG_KEY).apply();
    }

    public boolean getMainActivityRecreateFlag() {
        return sharedPreferences.getBoolean(MAIN_ACTIVITY_RECREATE_FLAG_KEY, false);
    }
}
