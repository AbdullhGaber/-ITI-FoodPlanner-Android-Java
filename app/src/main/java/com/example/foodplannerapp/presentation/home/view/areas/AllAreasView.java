package com.example.foodplannerapp.presentation.home.view.areas;

import com.example.foodplannerapp.data.model.meal_area.Area;

import java.util.List;

public interface AllAreasView {
    void showAreas(List<Area> areas);
    void showError(String msg);
    void showShimmer();
    void hideShimmer();
}
