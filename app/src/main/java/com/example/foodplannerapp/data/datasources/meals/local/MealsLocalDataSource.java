package com.example.foodplannerapp.data.datasources.meals.local;

import com.example.foodplannerapp.data.db.meals.entities.Meal;
import com.example.foodplannerapp.data.db.meals.entities.PlanMeal;

import java.util.List;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;

public interface MealsLocalDataSource {
    Flowable<List<Meal>> getMeals();
    Completable insertMeal(Meal meal);
    Completable deleteMeal(Meal meal);
    Maybe<Meal> getMealById(String mealId);
    Completable insertPlan(PlanMeal plan);
    Completable deletePlan(PlanMeal plan);
    Flowable<List<PlanMeal>> getPlansByDay(String day);
}
