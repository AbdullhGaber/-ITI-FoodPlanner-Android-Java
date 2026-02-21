package com.example.foodplannerapp.data.datasources.user;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import javax.inject.Inject;
import dagger.hilt.android.qualifiers.ApplicationContext;

public class UserPreferenceDataSource {
    private static final String PREF_NAME = "food_planner_prefs";
    private static final String KEY_IS_GUEST = "is_guest";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String KEY_USER_EMAIL = "user_email";
    private static final String KEY_USER_NAME = "user_name";
    private final SharedPreferences sharedPreferences;

    @Inject
    public UserPreferenceDataSource(@NonNull @ApplicationContext Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }
    public boolean isGuest() {
        return sharedPreferences.getBoolean(KEY_IS_GUEST, false);
    }
    public boolean isUserLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }
    public String getUserEmail() {
        return sharedPreferences.getString(KEY_USER_EMAIL, "Guest");
    }

    public void saveGuest(boolean isGuest){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_IS_GUEST, isGuest).apply();
    }
    public String getUsername() {
        return sharedPreferences.getString(KEY_USER_NAME, "user");
    }

    public void setLoginState(boolean isLoggedIn, String email, String username) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        if (isLoggedIn) {
            editor.putString(KEY_USER_EMAIL, email);
            editor.putString(KEY_USER_NAME, username);
        } else {
            editor.remove(KEY_USER_EMAIL);
            editor.remove(KEY_USER_NAME);
        }
        editor.apply();
    }
}