package com.example.foodplannerapp.presentation.home.presenter;

import com.example.foodplannerapp.data.reposetories.meals.MealsRepository;


import javax.inject.Inject;

public class HomePresenterImpl implements HomePresenter {
    private final MealsRepository mealsRepository;

    @Inject
    public HomePresenterImpl(MealsRepository mealsRepository) {
        this.mealsRepository = mealsRepository;
    }

    @Override
    public void getRandomMeal() {
        mealsRepository.getRandomMeal();
    }
}
