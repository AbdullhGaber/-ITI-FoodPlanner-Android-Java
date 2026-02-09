package com.example.foodplannerapp.data.reposetories.meals;

import com.example.foodplannerapp.data.datasources.meals.local.MealsLocalDataSource;
import com.example.foodplannerapp.data.datasources.meals.remote.MealsRemoteDataSource;
import com.example.foodplannerapp.data.model.meal_area.AreaListResponse;
import com.example.foodplannerapp.data.model.meal_category.CategoryResponse;

import javax.inject.Inject;
import io.reactivex.rxjava3.core.Single;

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

    @Override
    public Single<AreaListResponse> getAllAreas() {
        return mealsRemoteDataSource.getAllAreas();
    }

    @Override
    public Single<CategoryResponse> getAllCategories() {
        return mealsRemoteDataSource.getAllMealsCategories();
    }
}
