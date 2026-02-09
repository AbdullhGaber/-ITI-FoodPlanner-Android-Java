package com.example.foodplannerapp.data.datasources.di;

import com.example.foodplannerapp.data.datasources.meals.local.MealsLocalDataSource;
import com.example.foodplannerapp.data.datasources.meals.local.MealsLocalDataSourceImpl;
import com.example.foodplannerapp.data.datasources.meals.remote.MealsRemoteDataSource;
import com.example.foodplannerapp.data.datasources.meals.remote.MealsRemoteDataSourceImpl;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public interface DataSourceModule {
    @Binds
    MealsRemoteDataSource getRemoteMealDatasource(MealsRemoteDataSourceImpl mealsRemoteDataSource);
    @Binds
    MealsLocalDataSource getLocalMealDatasource(MealsLocalDataSourceImpl mealsLocalDataSource);
}
