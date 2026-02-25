package com.example.foodplannerapp.data.datasources.meals.local;
import com.example.foodplannerapp.data.db.meals.dao.MealDao;
import com.example.foodplannerapp.data.db.meals.entities.MealEntity;

import java.util.List;
import javax.inject.Inject;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;

public class MealsLocalDataSourceImpl implements MealsLocalDataSource {
    private final MealDao mealDao;
    @Inject
    public MealsLocalDataSourceImpl(MealDao mealDao) {
        this.mealDao = mealDao;
    }

    @Override
    public Flowable<Integer> getFavoritesCount() {
        return mealDao.getFavoritesCount();
    }

    @Override
    public Flowable<Integer> getPlansCount() {
        return mealDao.getPlansCount();
    }

    @Override
    public Flowable<List<MealEntity>> getFavMeals(){return mealDao.getAllFavMeals();}

    @Override
    public Completable insertMeals(List<MealEntity> meals) {
        return mealDao.insertMeals(meals);
    }

    @Override
    public Completable deleteAllMeals() {
        return mealDao.deleteAllMeals();
    }

    @Override
    public Completable insertMeal(MealEntity meal){return mealDao.insertMeal(meal);}
    @Override
    public Completable removeFavoriteMeal(String mealId){
        return mealDao.removeFavoriteMeal(mealId);
    }

    @Override
    public Completable deleteMeal(MealEntity mealEntity) {
        return mealDao.deleteMeal(mealEntity);
    }

    @Override
    public Completable removePlanMeal(String mealId){
        return mealDao.removePlanMeal(mealId);
    }

    @Override
    public Maybe<MealEntity> getMealById(String mealId) {
        return mealDao.getMealById(mealId);
    }

    @Override
    public Flowable<List<MealEntity>> getPlansByDay(String day) {
        return mealDao.getPlansByDay(day);
    }
}
