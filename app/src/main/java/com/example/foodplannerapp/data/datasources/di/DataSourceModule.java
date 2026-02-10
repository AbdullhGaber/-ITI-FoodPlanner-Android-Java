package com.example.foodplannerapp.data.datasources.di;

import android.content.Context;

import com.example.foodplannerapp.data.datasources.meals.local.MealsLocalDataSource;
import com.example.foodplannerapp.data.datasources.meals.local.MealsLocalDataSourceImpl;
import com.example.foodplannerapp.data.datasources.meals.remote.MealsRemoteDataSource;
import com.example.foodplannerapp.data.datasources.meals.remote.MealsRemoteDataSourceImpl;
import com.example.foodplannerapp.data.datasources.user.UserPreferenceDataSource;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public abstract class DataSourceModule {
    @Binds
    abstract MealsRemoteDataSource getRemoteMealDatasource(MealsRemoteDataSourceImpl mealsRemoteDataSource);
    @Binds
    abstract MealsLocalDataSource getLocalMealDatasource(MealsLocalDataSourceImpl mealsLocalDataSource);

    @Provides
    static UserPreferenceDataSource getUserPref(@ApplicationContext Context context){
        return new UserPreferenceDataSource(context);
    }
}
