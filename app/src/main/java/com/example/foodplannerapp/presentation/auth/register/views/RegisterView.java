package com.example.foodplannerapp.presentation.auth.register.views;

public interface RegisterView {
    void onRegisterSuccess();
    void onRegisterFailed(String title, String message);
}
