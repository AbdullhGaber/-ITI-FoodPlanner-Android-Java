package com.example.foodplannerapp.presentation.meals.presenter.meal_details;

import android.content.Context;
import com.example.foodplannerapp.data.model.meal.Meal;

public interface MealDetailsPresenter {
    void getMealDetails(String mealId);
    void addToFavorites(Meal meal, Context context);
    void removeFromFavorites(Meal meal);
    void onDestroy();
}
