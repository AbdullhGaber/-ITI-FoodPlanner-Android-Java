package com.example.foodplannerapp.data.db.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.foodplannerapp.data.db.meals.dao.MealDao;
import com.example.foodplannerapp.data.db.meals.dao.PlanDao;
import com.example.foodplannerapp.data.db.meals.entities.Meal;
import com.example.foodplannerapp.data.db.meals.entities.PlanMeal;


@Database(entities = {Meal.class, PlanMeal.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MealDao mealDao();
    public abstract PlanDao planDao();
}