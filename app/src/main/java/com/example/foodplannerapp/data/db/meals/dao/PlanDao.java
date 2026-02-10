package com.example.foodplannerapp.data.db.meals.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.foodplannerapp.data.db.meals.entities.PlanMeal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface PlanDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertPlan(PlanMeal plan);

    @Delete
    Completable deletePlan(PlanMeal plan);

    @Query("SELECT * FROM plan_table WHERE dayOfWeek = :day")
    Flowable<List<PlanMeal>> getPlansByDay(String day);
    @Query("SELECT * FROM plan_table")
    Flowable<List<PlanMeal>> getAllPlans();
}