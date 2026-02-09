package com.example.foodplannerapp.data.db.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.foodplannerapp.data.db.meals.dao.MealDao;
import com.example.foodplannerapp.data.db.meals.entities.Meal;


@Database(entities = {Meal.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MealDao mealDao();
}