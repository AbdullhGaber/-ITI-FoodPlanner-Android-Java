package com.example.foodplannerapp.data.reposetories.meals;

import com.example.foodplannerapp.data.datasources.meals.local.MealsLocalDataSource;
import com.example.foodplannerapp.data.datasources.meals.remote.MealsRemoteDataSource;
import com.example.foodplannerapp.data.model.meal.MealResponse;
import com.example.foodplannerapp.data.model.meal_area.Area;
import com.example.foodplannerapp.data.model.meal_area.AreaListResponse;
import com.example.foodplannerapp.data.model.meal_category.CategoryResponse;

import java.util.List;

import javax.inject.Inject;
import io.reactivex.rxjava3.core.Single;

public class MealsRepositoryImpl implements MealsRepository{
    MealsRemoteDataSource mealsRemoteDataSource;
    MealsLocalDataSource mealslocalDataSource;
    private List<Area> cachedAreas;
    @Inject
    public MealsRepositoryImpl(MealsRemoteDataSource mealsRemoteDataSource, MealsLocalDataSource mealslocalDataSource) {
        this.mealsRemoteDataSource = mealsRemoteDataSource;
        this.mealslocalDataSource = mealslocalDataSource;
    }

    @Override
    public Single<MealResponse> getRandomMeal() {
        return mealsRemoteDataSource.getRandomMeal();
    }

    @Override
    public Single<AreaListResponse> getAllAreas() {
        if (cachedAreas != null && !cachedAreas.isEmpty()) {
            return Single.just(new AreaListResponse(cachedAreas));
        }

        return mealsRemoteDataSource.getAllAreas()
                .doOnSuccess(response -> cachedAreas = response.getAreas());
    }

    @Override
    public Single<CategoryResponse> getAllCategories() {
        return mealsRemoteDataSource.getAllMealsCategories();
    }
}
