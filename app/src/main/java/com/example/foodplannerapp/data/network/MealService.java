package com.example.foodplannerapp.data.network;

import com.example.foodplannerapp.data.model.MealResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MealService {
    @GET("random.php")
    Call<MealResponse> getRandomMeal();
}
