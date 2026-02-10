package com.example.foodplannerapp.data.datasources.meals.local;
import com.example.foodplannerapp.data.db.meals.dao.MealDao;
import com.example.foodplannerapp.data.db.meals.entities.Meal;

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
    public Flowable<List<Meal>> getMeals(){return mealDao.getAllMeals();}
    @Override
    public Completable insertMeal(Meal meal){return mealDao.insertMeal(meal);}

    @Override
    public Completable deleteMeal(Meal meal) {
        return mealDao.deleteMeal(meal);
    }

    @Override
    public Maybe<Meal> getMealById(String mealId) {
        return mealDao.getMealById(mealId);
    }
}
