package com.example.foodplannerapp.data.datasources.meals.local;
import com.example.foodplannerapp.data.db.meals.dao.MealDao;
import com.example.foodplannerapp.data.db.meals.dao.PlanDao;
import com.example.foodplannerapp.data.db.meals.entities.Meal;
import com.example.foodplannerapp.data.db.meals.entities.PlanMeal;

import java.util.List;
import javax.inject.Inject;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;

public class MealsLocalDataSourceImpl implements MealsLocalDataSource {
    private final MealDao mealDao;
    private final PlanDao planDao;
    @Inject
    public MealsLocalDataSourceImpl(MealDao mealDao, PlanDao planDao) {
        this.mealDao = mealDao;
        this.planDao = planDao;
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

    @Override
    public Completable insertPlan(PlanMeal plan) {
        return planDao.insertPlan(plan) ;
    }

    @Override
    public Completable deletePlan(PlanMeal plan) {
        return planDao.deletePlan(plan);
    }

    @Override
    public Flowable<List<PlanMeal>> getPlansByDay(String day) {
        return planDao.getPlansByDay(day);
    }
}
