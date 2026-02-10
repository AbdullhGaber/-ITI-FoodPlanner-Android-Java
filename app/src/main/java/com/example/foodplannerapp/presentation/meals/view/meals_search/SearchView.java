package com.example.foodplannerapp.presentation.meals.view.meals_search;

import com.example.foodplannerapp.data.model.meal.Meal;
import java.util.List;

public interface SearchView {
    void showSearchResults(List<Meal> meals);
    void showError(String message);
}