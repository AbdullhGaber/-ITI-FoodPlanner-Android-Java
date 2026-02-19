package com.example.foodplannerapp.presentation.favorites.view;

import com.example.foodplannerapp.data.db.meals.entities.MealEntity;

import java.util.List;

public interface FavoriteView {
    void showFavoriteMeals(List<MealEntity> meals);
    void showError(String msg);
    void showSuccess(String msg);
}
