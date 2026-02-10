package com.example.foodplannerapp.data.datasources.meals.remote;


import com.example.foodplannerapp.data.model.meal.MealResponse;
import com.example.foodplannerapp.data.model.meal_area.AreaListResponse;
import com.example.foodplannerapp.data.model.meal_category.CategoryResponse;
import com.example.foodplannerapp.data.network.MealService;
import javax.inject.Inject;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;

public class MealsRemoteDataSourceImpl implements MealsRemoteDataSource{
    private final MealService mealService;

    @Inject
    public MealsRemoteDataSourceImpl(MealService mealService) {
        this.mealService = mealService;
    }

    @Override
    public Single<MealResponse> getRandomMeal() {
        return mealService.getRandomMeal();
    }

    @Override
    public Single<CategoryResponse> getAllMealsCategories() {
        return mealService.getAllMealsCategories();
    }

    @Override
    public Maybe<MealResponse> getMealById(String id) {
        return mealService.getMealById(id);
    }

    @Override
    public Single<AreaListResponse> getAllAreas() {
        return mealService.getAllArea();
    }
    @Override
    public Single<MealResponse> searchByName(String query) {
        return mealService.searchByName(query);
    }

    @Override
    public Single<MealResponse> searchByIngredient(String query) {
        return mealService.filterByIngredient(query);
    }

    @Override
    public Single<MealResponse> searchByArea(String query) {
        return mealService.filterByArea(query);
    }

    @Override
    public Single<MealResponse> searchByCategory(String query) {
        return mealService.filterByCategory(query);
    }
}
