package com.example.foodplannerapp.data.network;

import com.example.foodplannerapp.data.model.meal.MealResponse;
import com.example.foodplannerapp.data.model.meal_category.CategoryResponse;
import com.example.foodplannerapp.data.model.meal_ingeredient.IngredientResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MealService {
    @GET("random.php")
    Call<MealResponse> getRandomMeal();

    @GET("categories.php")
    Call<CategoryResponse> getAllMealsCategories();

    @GET("list.php?i=list")
    Call<IngredientResponse> getAllIngredients();
}
