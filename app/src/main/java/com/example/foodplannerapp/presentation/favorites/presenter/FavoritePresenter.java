package com.example.foodplannerapp.presentation.favorites.presenter;

import com.example.foodplannerapp.data.db.meals.entities.Meal;

public interface FavoritePresenter {
    void getFavMeals();
    void onDestroy();
    void deleteMeal(Meal meal);
}
