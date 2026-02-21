package com.example.foodplannerapp.presentation.home.presenter.home;

public interface HomePresenter {
    void observeRandomMeal();
    void onRandomMealClick();
    void observeAllAreas();
    void observeAllCategories();

    boolean isGuest();
    String getUsername();
    void onDestroy();
}
