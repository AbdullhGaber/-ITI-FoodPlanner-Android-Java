package com.example.foodplannerapp.data.datasources.meals.remote;

import androidx.annotation.NonNull;
import com.example.foodplannerapp.data.model.meal.MealResponse;
import com.example.foodplannerapp.data.model.meal_category.CategoryResponse;
import com.example.foodplannerapp.data.network.MealService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealsRemoteDataSourceImpl implements MealsRemoteDataSource{
    private final MealService mealService;
    public MealsRemoteDataSourceImpl(MealService mealService) {
        this.mealService = mealService;
    }

    @Override
    public void getRandomMeal() {
        mealService.getRandomMeal()
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<MealResponse> call, @NonNull Response<MealResponse> response) {

                    }

                    @Override
                    public void onFailure(@NonNull Call<MealResponse> call, @NonNull Throwable t) {

                    }
                });
    }

    @Override
    public void getAllMealsCategories() {
        mealService.getAllMealsCategories()
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<CategoryResponse> call, @NonNull Response<CategoryResponse> response) {

                    }

                    @Override
                    public void onFailure(@NonNull Call<CategoryResponse> call, @NonNull Throwable t) {

                    }
                });
    }
}
