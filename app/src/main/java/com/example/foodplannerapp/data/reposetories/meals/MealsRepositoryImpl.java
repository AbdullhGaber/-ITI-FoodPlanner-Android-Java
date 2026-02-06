package com.example.foodplannerapp.data.reposetories.meals;

import com.example.foodplannerapp.data.datasources.meals.local.MealsLocalDataSource;
import com.example.foodplannerapp.data.datasources.meals.remote.MealsRemoteDataSource;

import javax.inject.Inject;

public class MealsRepositoryImpl implements MealsRepository{
    MealsRemoteDataSource mealsRemoteDataSource;
    MealsLocalDataSource mealslocalDataSource;
    @Inject
    public MealsRepositoryImpl(MealsRemoteDataSource mealsRemoteDataSource, MealsLocalDataSource mealslocalDataSource) {
        this.mealsRemoteDataSource = mealsRemoteDataSource;
        this.mealslocalDataSource = mealslocalDataSource;
    }

    @Override
    public void getRandomMeal() {
        mealsRemoteDataSource.getRandomMeal();
    }
}
