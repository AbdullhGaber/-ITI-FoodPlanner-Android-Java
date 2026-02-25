package com.example.foodplannerapp.presentation.profile.view;

public interface ProfileView {
    void showError(String msg);
    void showSuccess(String msg);
    void showFavoritesCount(int count);
    void showPlansCount(int count);
    void displayUserData(String username, String email);
    void navigateToLogin();
}
