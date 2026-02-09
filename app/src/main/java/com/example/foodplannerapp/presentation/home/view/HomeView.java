package com.example.foodplannerapp.presentation.home.view;

import com.example.foodplannerapp.data.model.meal_area.Area;
import java.util.List;
public interface HomeView {
    void showAreas(List<Area> areas);
    void showError(String msg);
    void showShimmer();
    void hideShimmer();
}
