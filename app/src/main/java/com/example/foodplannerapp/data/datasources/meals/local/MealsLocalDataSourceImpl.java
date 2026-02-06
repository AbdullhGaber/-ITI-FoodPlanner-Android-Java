package com.example.foodplannerapp.data.datasources.meals.local;
import com.example.foodplannerapp.data.db.meals.dao.MealDao;
import com.example.foodplannerapp.data.db.meals.entities.Meal;
import javax.inject.Inject;

public class MealsLocalDataSourceImpl implements MealsLocalDataSource {
    private final MealDao mealDao;

    @Inject
    public MealsLocalDataSourceImpl(MealDao mealDao) {
        this.mealDao = mealDao;
    }

    @Override
    public void getMeals() { }

    @Override
    public void insertMeal(Meal meal) { }
}
