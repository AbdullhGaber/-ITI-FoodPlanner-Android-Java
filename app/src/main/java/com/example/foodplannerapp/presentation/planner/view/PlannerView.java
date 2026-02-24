package com.example.foodplannerapp.presentation.planner.view;

import com.example.foodplannerapp.data.db.meals.entities.MealEntity;
import com.example.foodplannerapp.data.db.meals.entities.PlanMeal;
import com.example.foodplannerapp.data.model.meal.Meal;

import java.util.List;

public interface PlannerView {
    void showPlannedMeals(List<MealEntity> plans);
    void showError(String title, String message);
    void showUndoPlanSnackBar(MealEntity deletedPlan);
    void showRestoredPlanSnackBar();
}