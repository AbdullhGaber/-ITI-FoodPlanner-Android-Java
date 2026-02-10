package com.example.foodplannerapp.presentation.favorites.view;

import com.example.foodplannerapp.data.db.meals.entities.Meal;

import java.util.List;

public interface FavoriteView {
    void showFavoriteMeals(List<Meal> meals);
    void showError(String msg);
    void showSuccess(String msg);
}
