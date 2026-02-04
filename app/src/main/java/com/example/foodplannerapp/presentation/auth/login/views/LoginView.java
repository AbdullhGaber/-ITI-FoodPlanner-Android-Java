package com.example.foodplannerapp.presentation.auth.login.views;

public interface LoginView {
    void onLoginSuccess();
    void  onLoginFailed(String title, String message);
}
