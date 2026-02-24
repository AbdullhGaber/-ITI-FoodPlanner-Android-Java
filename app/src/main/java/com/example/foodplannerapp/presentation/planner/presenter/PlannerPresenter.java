package com.example.foodplannerapp.presentation.planner.presenter;

import com.example.foodplannerapp.data.db.meals.entities.MealEntity;



public interface PlannerPresenter {
    boolean isGuest();
    void removeUserLoginState();
    void getMealsForDay(String day);
    void deletePlan(MealEntity meal);
    void insertPlan(MealEntity meal);
    void onDestroy();
}