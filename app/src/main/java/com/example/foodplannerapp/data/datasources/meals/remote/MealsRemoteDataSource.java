package com.example.foodplannerapp.data.datasources.meals.remote;

public interface MealsRemoteDataSource {
    void getRandomMeal();
    void getAllMealsCategories();
    void getAllIngredients();
}
