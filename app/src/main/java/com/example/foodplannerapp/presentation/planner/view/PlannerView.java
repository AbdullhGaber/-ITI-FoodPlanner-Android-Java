package com.example.foodplannerapp.presentation.planner.view;

import com.example.foodplannerapp.data.db.meals.entities.PlanMeal;
import java.util.List;

public interface PlannerView {
    void showPlannedMeals(List<PlanMeal> plans);
    void showError(String message);
    void showSuccess(String message);
}