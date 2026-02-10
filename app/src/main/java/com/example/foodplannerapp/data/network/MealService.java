package com.example.foodplannerapp.data.network;

import com.example.foodplannerapp.data.model.meal.MealResponse;
import com.example.foodplannerapp.data.model.meal_area.AreaListResponse;
import com.example.foodplannerapp.data.model.meal_category.CategoryResponse;
import com.example.foodplannerapp.data.model.meal_ingeredient.IngredientResponse;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealService {
    @GET("random.php")
    Single<MealResponse> getRandomMeal();
    @GET("search.php")
    Single<MealResponse> searchByName(@Query("s") String query);

    @GET("filter.php")
    Single<MealResponse> filterByIngredient(@Query("i") String query);

    @GET("filter.php")
    Single<MealResponse> filterByArea(@Query("a") String query);

    @GET("filter.php")
    Single<MealResponse> filterByCategory(@Query("c") String query);
    @GET("lookup.php")
    Maybe<MealResponse> getMealById(@Query("i") String id);

    @GET("categories.php")
    Single<CategoryResponse> getAllMealsCategories();

    @GET("list.php?a=list")
    Single<AreaListResponse> getAllArea();
}
