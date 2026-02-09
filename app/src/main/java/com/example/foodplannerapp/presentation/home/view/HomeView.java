package com.example.foodplannerapp.presentation.home.view;

import com.example.foodplannerapp.data.model.meal_area.Area;
import com.example.foodplannerapp.data.model.meal_category.Category;

import java.util.List;
public interface HomeView {
    void showAreas(List<Area> areas);
    void showCategories(List<Category> categories);
    void showError(String msg);
    void showAreaShimmer();
    void hideAreaShimmer();
    void showCategoryShimmer();
    void hideCategoryShimmer();
}
