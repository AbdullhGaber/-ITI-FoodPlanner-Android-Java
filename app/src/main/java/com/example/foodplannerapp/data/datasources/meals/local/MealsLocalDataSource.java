package com.example.foodplannerapp.data.datasources.meals.local;

import com.example.foodplannerapp.data.db.meals.entities.Meal;

public interface MealsLocalDataSource {
    void getMeals();
    void insertMeal(Meal meal);
}
