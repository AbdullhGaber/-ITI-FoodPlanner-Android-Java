package com.example.foodplannerapp.data.reposetories.meals;

import com.example.foodplannerapp.data.model.meal.MealResponse;
import com.example.foodplannerapp.data.model.meal_area.AreaListResponse;
import com.example.foodplannerapp.data.model.meal_category.CategoryResponse;

import io.reactivex.rxjava3.core.Single;

public interface MealsRepository {
    Single<MealResponse> getRandomMeal();
    Single<AreaListResponse> getAllAreas();
    Single<CategoryResponse> getAllCategories();
}
