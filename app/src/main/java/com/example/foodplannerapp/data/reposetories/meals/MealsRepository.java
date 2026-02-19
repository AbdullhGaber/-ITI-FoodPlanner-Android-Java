package com.example.foodplannerapp.data.reposetories.meals;

import com.example.foodplannerapp.data.db.meals.entities.MealEntity;
import com.example.foodplannerapp.data.db.meals.entities.PlanMeal;
import com.example.foodplannerapp.data.model.meal.Meal;
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
    Flowable<List<MealEntity>> getFavMeals();
    Completable insertMeal(MealEntity meal);
    Completable deleteMeal(MealEntity mealEntity);

    Single<Meal> getMealDetails(String mealId);
    Single<List<Meal>> searchMeals(String query, SearchType type);
    enum SearchType {
        NAME, INGREDIENT, AREA, CATEGORY
    }
    Completable insertPlan(MealEntity meal);
    Completable removeFavoriteMeal(String mealId);
    Completable removePlanMeal(String mealId);
    Flowable<List<MealEntity>> getPlansByDay(String day);
}
