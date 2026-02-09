package com.example.foodplannerapp.data.datasources.meals.remote;

import com.example.foodplannerapp.data.model.meal_area.AreaListResponse;
import io.reactivex.rxjava3.core.Single;

public interface MealsRemoteDataSource {
    void getRandomMeal();
    void getAllMealsCategories();
    void getAllIngredients();
    Single<AreaListResponse> getAllAreas();
}
