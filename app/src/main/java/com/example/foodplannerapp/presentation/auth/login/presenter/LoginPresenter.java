package com.example.foodplannerapp.presentation.auth.login.presenter;

import android.content.Context;

public interface LoginPresenter {
    void login(String email, String password);
    void loginWithGoogle(String idToken);
    void onDestroy();
}
