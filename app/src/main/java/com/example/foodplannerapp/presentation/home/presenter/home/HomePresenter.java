package com.example.foodplannerapp.presentation.home.presenter.home;

import com.example.foodplannerapp.data.model.meal.Meal;

public interface HomePresenter {
    void observeRandomMeal();
    void onRandomMealClick();
    void onCarouselMealClick(Meal meal);
    void observeAllAreas();
    void observeAllCategories();
    boolean isGuest();
    String getUsername();
    void onDestroy();
    void startAutoScroll();
    void stopAutoScroll();
    void observeCarouselMeals();
}
