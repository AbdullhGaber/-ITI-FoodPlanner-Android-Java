package com.example.foodplannerapp.data.reposetories.meals;

import com.example.foodplannerapp.data.db.meals.entities.Meal;
import com.example.foodplannerapp.data.model.meal.MealResponse;
import com.example.foodplannerapp.data.model.meal_area.AreaListResponse;
import com.example.foodplannerapp.data.model.meal_category.CategoryResponse;

import java.util.List;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public interface MealsRepository {
    Single<MealResponse> getRandomMeal();
    Single<AreaListResponse> getAllAreas();
    Single<CategoryResponse> getAllCategories();

    Flowable<List<Meal>> getFavMeals();
    Completable insertMeal(Meal meal);
    Completable deleteMeal(Meal meal);
    Single<com.example.foodplannerapp.data.model.meal.Meal> getMealDetails(String mealId);
}
