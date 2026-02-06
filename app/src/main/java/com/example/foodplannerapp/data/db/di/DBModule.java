package com.example.foodplannerapp.data.db.di;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Room;
import com.example.foodplannerapp.data.db.meals.dao.MealDao;
import com.example.foodplannerapp.data.db.room.AppDatabase;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DBModule {
    @Provides
    @Singleton
    public AppDatabase provideDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(
                context,
                AppDatabase.class,
                "food_planner_db"
        ).fallbackToDestructiveMigration()
         .build();
    }

    @Provides
    @Singleton
    public MealDao provideMealDao(@NonNull AppDatabase database) {
        return database.mealDao();
    }
}