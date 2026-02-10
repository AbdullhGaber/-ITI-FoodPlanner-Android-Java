package com.example.foodplannerapp.presentation.profile.view;

public interface ProfileView {
    void showError(String msg);
    void showSuccess(String msg);
    void navigateToLogin();
}
