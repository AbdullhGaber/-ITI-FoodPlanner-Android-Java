package com.example.foodplannerapp.presentation.meals.presenter.meals_search;
import com.example.foodplannerapp.data.reposetories.meals.MealsRepository;
public interface SearchPresenter {
    void search(String query);
    void setSearchType(MealsRepository.SearchType type);
    void onDestroy();
}