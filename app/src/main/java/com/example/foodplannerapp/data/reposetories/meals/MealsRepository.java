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
    Flowable<Integer> getFavoritesCount();
    Flowable<Integer> getPlansCount();
    Single<MealResponse> getRandomMeal();
    Single<List<Meal>> getRandomMealsBatch(int count);
    Single<AreaListResponse> getAllAreas();
    Single<CategoryResponse> getAllCategories();
    Flowable<List<MealEntity>> getFavMeals();

    Single<Meal> getMealDetails(String mealId);
    Single<List<Meal>> searchMeals(String query, SearchType type);
    enum SearchType {
        NAME, INGREDIENT, AREA, CATEGORY
    }
    Completable deleteAllMeals();
    Completable insertPlanMeal(MealEntity meal);
    Completable insertFavorite(MealEntity meal);
    Completable removeFavoriteMeal(String mealId);
    Completable removePlanMeal(String mealId);
    Flowable<List<MealEntity>> getPlansByDay(String day);

}
