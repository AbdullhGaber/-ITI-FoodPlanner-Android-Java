package com.example.foodplannerapp.data.reposetories.meals;

import com.example.foodplannerapp.data.db.meals.entities.Meal;
import com.example.foodplannerapp.data.db.meals.entities.PlanMeal;
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
    Single<List<com.example.foodplannerapp.data.model.meal.Meal>> searchMeals(String query, SearchType type);
    enum SearchType {
        NAME, INGREDIENT, AREA, CATEGORY
    }
    Completable insertPlan(PlanMeal plan);
    Completable deletePlan(PlanMeal plan);
    Flowable<List<PlanMeal>> getPlansByDay(String day);
}
