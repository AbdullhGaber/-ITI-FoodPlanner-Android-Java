package com.example.foodplannerapp.data.reposetories.meals;

import com.example.foodplannerapp.data.datasources.meals.local.MealsLocalDataSource;
import com.example.foodplannerapp.data.datasources.meals.remote.MealsRemoteDataSource;
import com.example.foodplannerapp.data.datasources.user.UserPreferenceDataSource;
import com.example.foodplannerapp.data.db.meals.entities.MealEntity;
import com.example.foodplannerapp.data.model.meal.Meal;
import com.example.foodplannerapp.data.model.meal.MealResponse;
import com.example.foodplannerapp.data.model.meal_area.AreaListResponse;
import com.example.foodplannerapp.data.model.meal_category.CategoryResponse;
import com.example.foodplannerapp.data.utils.MealMapper;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class MealsRepositoryImpl implements MealsRepository{
    private final MealsRemoteDataSource mealsRemoteDataSource;
    private final MealsLocalDataSource mealslocalDataSource;
    private final UserPreferenceDataSource userPref;
    @Inject
    public MealsRepositoryImpl(
            MealsRemoteDataSource mealsRemoteDataSource,
            MealsLocalDataSource mealslocalDataSource,
            UserPreferenceDataSource userPref
    ) {
        this.mealsRemoteDataSource = mealsRemoteDataSource;
        this.mealslocalDataSource = mealslocalDataSource;
        this.userPref = userPref;
    }

    @Override
    public Flowable<Integer> getFavoritesCount() {
        return mealslocalDataSource.getFavoritesCount();
    }

    @Override
    public Flowable<Integer> getPlansCount() {
        return mealslocalDataSource.getPlansCount();
    }

    @Override
    public Single<MealResponse> getRandomMeal() {
        return mealsRemoteDataSource.getRandomMeal();
    }

    @Override
    public Single<List<Meal>> getRandomMealsBatch(int count) {
        return Observable.range(0, count)
                .flatMapSingle(i -> mealsRemoteDataSource.getRandomMeal())
                .map(response -> response.getMeals().get(0))
                .toList();
    }

    @Override
    public Single<AreaListResponse> getAllAreas() {
        return mealsRemoteDataSource.getAllAreas();
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
    public Completable removeFavoriteMeal(String mealId){
        return mealslocalDataSource
                .removeFavoriteMeal(mealId)
                .andThen(mealsRemoteDataSource.removeBackupFavorite(userPref.getUserId(), mealId)
                        .onErrorComplete());
    }
    @Override
    public Completable removePlanMeal(String mealId){
        return mealslocalDataSource
                .removePlanMeal(mealId)
                .andThen(mealsRemoteDataSource.removeBackupPlan(userPref.getUserId(), mealId)
                        .onErrorComplete());
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
    public Completable deleteAllMeals() {
        return mealslocalDataSource.deleteAllMeals();
    }

    @Override
    public Completable insertPlanMeal(MealEntity meal) {
        return mealslocalDataSource
                .insertMeal(meal)
                .andThen(mealsRemoteDataSource.backupPlan(userPref.getUserId(), meal)
                        .onErrorComplete());
    }

    @Override
    public Completable insertFavorite(MealEntity meal) {
        return mealslocalDataSource
                .insertMeal(meal)
                .andThen(mealsRemoteDataSource.backupFavorite(userPref.getUserId(), meal)
                        .onErrorComplete());
    }

    @Override
    public Flowable<List<MealEntity>> getPlansByDay(String day) {
        return mealslocalDataSource.getPlansByDay(day);
    }
}
