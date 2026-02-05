package com.example.foodplannerapp.data.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealClient {
    private static MealService mealService;
    private final static String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    public static MealService getMealService(){
        if(mealService == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mealService = retrofit.create(MealService.class);
        }

        return mealService;
    }
}
