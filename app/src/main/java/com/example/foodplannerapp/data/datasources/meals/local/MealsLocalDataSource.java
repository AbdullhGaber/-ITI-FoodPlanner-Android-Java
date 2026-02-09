package com.example.foodplannerapp.data.datasources.meals.local;

import com.example.foodplannerapp.data.db.meals.entities.Meal;
import java.util.List;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public interface MealsLocalDataSource {
    Flowable<List<Meal>> getMeals();
    Completable insertMeal(Meal meal);
}
