package com.example.foodplannerapp.data.datasources.meals.remote;

import com.example.foodplannerapp.data.db.meals.entities.MealEntity;
import com.example.foodplannerapp.data.model.meal.MealResponse;
import com.example.foodplannerapp.data.model.meal_area.AreaListResponse;
import com.example.foodplannerapp.data.model.meal_category.CategoryResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;

public interface MealsRemoteDataSource {
    Single<MealResponse> getRandomMeal();
    Single<CategoryResponse> getAllMealsCategories();
    Maybe<MealResponse> getMealById(String id);
    Single<AreaListResponse> getAllAreas();
    Single<MealResponse> searchByName(String query);
    Single<MealResponse> searchByIngredient(String query);
    Single<MealResponse> searchByArea(String query);
    Single<MealResponse> searchByCategory(String query);
    Completable backupFavorite(String userId, MealEntity meal);
    Completable removeBackupFavorite(String userId, String mealId);
    Completable backupPlan(String userId, MealEntity meal);
    Completable removeBackupPlan(String userId, String mealId);
    Single<List<MealEntity>> getBackedUpFavorites(String userId);
    Single<List<MealEntity>> getBackedUpPlans(String userId);
}
