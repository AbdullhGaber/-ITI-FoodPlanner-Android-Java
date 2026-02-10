package com.example.foodplannerapp.data.reposetories.meals;

import com.example.foodplannerapp.data.datasources.meals.local.MealsLocalDataSource;
import com.example.foodplannerapp.data.datasources.meals.remote.MealsRemoteDataSource;
import com.example.foodplannerapp.data.db.meals.entities.Meal;
import com.example.foodplannerapp.data.model.meal.MealResponse;
import com.example.foodplannerapp.data.model.meal_area.Area;
import com.example.foodplannerapp.data.model.meal_area.AreaListResponse;
import com.example.foodplannerapp.data.model.meal_category.CategoryResponse;
import com.example.foodplannerapp.data.utils.MealMapper;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
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

    @Override
    public Flowable<List<Meal>> getFavMeals() {
        return mealslocalDataSource.getMeals();
    }

    @Override
    public Completable insertMeal(Meal meal) {
        return mealslocalDataSource.insertMeal(meal);
    }

    @Override
    public Completable deleteMeal(Meal meal) {
        return mealslocalDataSource.deleteMeal(meal);
    }
    @Override
    public Single<com.example.foodplannerapp.data.model.meal.Meal> getMealDetails(String mealId) {
        return mealslocalDataSource.getMealById(mealId)
                .map(MealMapper::toModel).switchIfEmpty(
                        mealsRemoteDataSource.getMealById(mealId)
                                .map(response -> response.getMeals().get(0))
                ).toSingle();
    }

    @Override
    public Single<List<com.example.foodplannerapp.data.model.meal.Meal>> searchMeals(String query, SearchType type) {
        Single<MealResponse> responseSingle;

        switch (type) {
            case INGREDIENT:
                responseSingle = mealsRemoteDataSource.searchByIngredient(query);
                break;
            case AREA:
                responseSingle = mealsRemoteDataSource.searchByArea(query);
                break;
            case CATEGORY:
                responseSingle = mealsRemoteDataSource.searchByCategory(query);
                break;
            case NAME:
            default:
                responseSingle = mealsRemoteDataSource.searchByName(query);
                break;
        }

        return responseSingle.map(response -> {
            if (response.getMeals() != null) {
                return response.getMeals();
            } else {
                return Collections.emptyList();
            }
        });
    }
}
