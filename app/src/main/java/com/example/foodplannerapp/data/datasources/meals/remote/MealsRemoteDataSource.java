package com.example.foodplannerapp.data.datasources.meals.remote;

import com.example.foodplannerapp.data.model.meal_area.AreaListResponse;
import com.example.foodplannerapp.data.model.meal_category.CategoryResponse;

import io.reactivex.rxjava3.core.Single;

public interface MealsRemoteDataSource {
    void getRandomMeal();
    Single<CategoryResponse> getAllMealsCategories();
    void getAllIngredients();
    Single<AreaListResponse> getAllAreas();
}
