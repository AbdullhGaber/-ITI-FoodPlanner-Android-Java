package com.example.foodplannerapp.presentation.meals.view.meals_details;

import com.example.foodplannerapp.data.model.meal.Meal;

public interface MealDetailsView {
    void showMealDetails(Meal meal);
    void showLoading();
    void hideLoading();
    void showError(String msg);
    void showSuccess(String msg);
}
