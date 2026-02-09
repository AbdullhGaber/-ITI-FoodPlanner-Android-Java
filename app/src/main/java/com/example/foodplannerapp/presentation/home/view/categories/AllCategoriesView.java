package com.example.foodplannerapp.presentation.home.view.categories;

import com.example.foodplannerapp.data.model.meal_area.Area;
import com.example.foodplannerapp.data.model.meal_category.Category;

import java.util.List;

public interface AllCategoriesView {
    void showCategories(List<Category> areas);
    void showError(String msg);
    void showShimmer();
    void hideShimmer();
}
