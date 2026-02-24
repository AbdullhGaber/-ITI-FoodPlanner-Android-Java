package com.example.foodplannerapp.data.datasources.meals.local;

import com.example.foodplannerapp.data.db.meals.entities.MealEntity;
import java.util.List;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;

public interface MealsLocalDataSource {
    Flowable<List<MealEntity>> getFavMeals();
    Completable insertMeals(List<MealEntity> meals);
    Completable deleteAllMeals();
    Completable insertMeal(MealEntity meal);
    Completable removeFavoriteMeal(String mealId);
    Completable deleteMeal(MealEntity mealEntity);
    Completable removePlanMeal(String mealId);
    Maybe<MealEntity> getMealById(String mealId);
    Flowable<List<MealEntity>> getPlansByDay(String day);
}
