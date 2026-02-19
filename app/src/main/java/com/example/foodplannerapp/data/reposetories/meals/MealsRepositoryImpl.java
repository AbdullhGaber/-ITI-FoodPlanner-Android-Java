package com.example.foodplannerapp.data.reposetories.meals;

import com.example.foodplannerapp.data.datasources.meals.local.MealsLocalDataSource;
import com.example.foodplannerapp.data.datasources.meals.remote.MealsRemoteDataSource;
import com.example.foodplannerapp.data.db.meals.entities.MealEntity;
import com.example.foodplannerapp.data.model.meal.Meal;
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
    private final MealsRemoteDataSource mealsRemoteDataSource;
    private final MealsLocalDataSource mealslocalDataSource;
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
    public Flowable<List<MealEntity>> getFavMeals() {
        return mealslocalDataSource.getFavMeals();
    }

    @Override
    public Completable insertMeal(MealEntity meal) {
        return mealslocalDataSource.insertMeal(meal);
    }
    @Override
    public Completable removeFavoriteMeal(String mealId){
        return mealslocalDataSource.removeFavoriteMeal(mealId);
    }
    @Override
    public Completable removePlanMeal(String mealId){
        return mealslocalDataSource.removePlanMeal(mealId);
    }
    @Override
    public Single<Meal> getMealDetails(String mealId) {
        return mealslocalDataSource.getMealById(mealId)
                .map(MealMapper::toModel).switchIfEmpty(
                        mealsRemoteDataSource.getMealById(mealId)
                                .map(response -> response.getMeals().get(0))
                ).toSingle();
    }

    @Override
    public Single<List<Meal>> searchMeals(String query, SearchType type) {
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
    @Override
    public Completable insertPlan(MealEntity meal) {
        return mealslocalDataSource.insertMeal(meal);
    }

    @Override
    public Completable deleteMeal(MealEntity mealEntity) {
        return mealslocalDataSource.deleteMeal(mealEntity);
    }

    @Override
    public Flowable<List<MealEntity>> getPlansByDay(String day) {
        return mealslocalDataSource.getPlansByDay(day);
    }
}
