package com.example.foodplannerapp.data.datasources.user;

import android.content.Context;
import android.content.SharedPreferences;
import javax.inject.Inject;
import dagger.hilt.android.qualifiers.ApplicationContext;

public class UserPreferenceDataSource {
    private static final String PREF_NAME = "food_planner_prefs";
    private static final String KEY_IS_GUEST = "is_guest";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String KEY_USER_EMAIL = "user_email";

    private final SharedPreferences sharedPreferences;

    @Inject
    public UserPreferenceDataSource(@ApplicationContext Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }
    public void saveGuestMode(boolean isGuest) {
        sharedPreferences.edit().putBoolean(KEY_IS_GUEST, isGuest).apply();
    }
    public boolean isGuest() {
        return sharedPreferences.getBoolean(KEY_IS_GUEST, false);
    }
    public void setGuestMode(boolean isGuest) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_IS_GUEST, isGuest);
        if (isGuest) {
            editor.putBoolean(KEY_IS_LOGGED_IN, false);
            editor.remove(KEY_USER_EMAIL);
        }
        editor.apply();
    }

    public void setLoginState(boolean isLoggedIn, String email) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        if (isLoggedIn) {
            editor.putString(KEY_USER_EMAIL, email);
        } else {
            editor.remove(KEY_USER_EMAIL);
        }
        editor.apply();
    }

    public boolean isUserLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }
    
    public String getUserEmail() {
        return sharedPreferences.getString(KEY_USER_EMAIL, "Guest");
    }
}