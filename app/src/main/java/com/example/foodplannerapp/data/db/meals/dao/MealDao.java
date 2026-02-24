package com.example.foodplannerapp.data.db.meals.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.foodplannerapp.data.db.meals.entities.MealEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;

@Dao
public abstract class MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract Completable insertMeals(List<MealEntity> meals);

    @Query("DELETE FROM meals_table")
    public abstract Completable deleteAllMeals();
    @Query("SELECT * FROM meals_table WHERE isFav = 1")
    public abstract Flowable<List<MealEntity>> getAllFavMeals();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract Completable insertMeal(MealEntity meal);

    @Delete
    public abstract Completable deleteMeal(MealEntity mealEntity);

    @Query("SELECT * FROM meals_table WHERE idMeal = :id")
    public abstract Maybe<MealEntity> getMealById(String id);

    @Query("SELECT * FROM meals_table WHERE dayOfWeek LIKE :day || '%' ")
    public abstract Flowable<List<MealEntity>> getPlansByDay(String day);
    @Query("UPDATE meals_table SET isFav = 0 WHERE idMeal = :mealId")
    protected abstract void internalRemoveFavoriteFlagSync(String mealId);

    @Query("DELETE FROM meals_table WHERE idMeal = :mealId AND (dayOfWeek IS NULL OR dayOfWeek = '')")
    protected abstract void internalCleanUpOrphanedMealSync(String mealId);

    @Transaction
    protected void executeFavoriteRemovalTransaction(String mealId) {
        internalRemoveFavoriteFlagSync(mealId);
        internalCleanUpOrphanedMealSync(mealId);
    }

    public Completable removeFavoriteMeal(String mealId) {
        return Completable.fromAction(() -> executeFavoriteRemovalTransaction(mealId));
    }

    @Query("UPDATE meals_table SET dayOfWeek = null WHERE idMeal = :mealId")
    protected abstract void internalRemovePlanFlagSync(String mealId);

    @Query("DELETE FROM meals_table WHERE idMeal = :mealId AND isFav = 0")
    protected abstract void internalCleanUpOrphanedPlanSync(String mealId);

    @Transaction
    protected void executePlanRemovalTransaction(String mealId) {
        internalRemovePlanFlagSync(mealId);
        internalCleanUpOrphanedPlanSync(mealId);
    }

    public Completable removePlanMeal(String mealId) {
        return Completable.fromAction(() -> executePlanRemovalTransaction(mealId));
    }
}