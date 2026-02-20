package com.example.foodplannerapp.presentation.planner.presenter;

import android.content.Context;
import com.example.foodplannerapp.data.db.meals.entities.MealEntity;
import com.example.foodplannerapp.data.model.meal.Meal;


public interface PlannerPresenter {
    void getMealsForDay(String day);
    void deletePlan(MealEntity meal);
    void insertPlan(MealEntity meal);
    void onDestroy();
}