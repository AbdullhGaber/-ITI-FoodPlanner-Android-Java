package com.example.foodplannerapp.presentation.planner.presenter;

import com.example.foodplannerapp.data.db.meals.entities.PlanMeal;

public interface PlannerPresenter {
    void getMealsForDay(String day);
    void deletePlan(PlanMeal plan);
    void onDestroy();
}