package com.example.foodplannerapp.data.network;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MealService {
    @GET("/random")
    Call<Object> getRandomMeal();
}
