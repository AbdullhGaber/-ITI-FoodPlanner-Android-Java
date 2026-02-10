package com.example.foodplannerapp.presentation.meals.presenter;

import android.content.Context;
import com.example.foodplannerapp.data.db.meals.entities.Meal;
import com.example.foodplannerapp.data.reposetories.meals.MealsRepository;
import com.example.foodplannerapp.data.utils.MealMapper;
import com.example.foodplannerapp.presentation.meals.view.MealDetailsView;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealDetailsPresenterImpl implements MealDetailsPresenter{
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final MealsRepository mealsRepository;
    private final MealDetailsView view;

    @Inject
    public MealDetailsPresenterImpl(MealsRepository mealsRepository, MealDetailsView view) {
        this.mealsRepository = mealsRepository;
        this.view = view;
    }

    @Override
    public void getMealDetails(String mealId) {
        view.showLoading(); // Optional: Show a progress bar

        Disposable d = mealsRepository.getMealDetails(mealId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        meal -> {
                            view.hideLoading();
                            view.showMealDetails(meal);
                        },
                        error -> {
                            view.hideLoading();
                            view.showError(error.getMessage());
                        }
                );
        compositeDisposable.add(d);
    }

    @Override
    public void addToFavorites(com.example.foodplannerapp.data.model.meal.Meal meal, Context context) {
        if (meal == null) return;

        // Convert Model to DB Entity
        Meal dbMeal = MealMapper.toEntity(meal, context);

        Disposable d = mealsRepository.insertMeal(dbMeal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> view.showSuccess("Added to Favorites"),
                        error -> view.showError("Failed to add favorite: " + error.getMessage())
                );
        compositeDisposable.add(d);
    }

    @Override
    public void removeFromFavorites(com.example.foodplannerapp.data.model.meal.Meal meal) {
        if (meal == null) return;

        // Convert Model to DB Entity (we just need the ID for deletion)
        Meal dbMeal = new Meal();
        dbMeal.setIdMeal(meal.getIdMeal());
        dbMeal.setStrMeal(meal.getStrMeal());

        Disposable d = mealsRepository.deleteMeal(dbMeal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> view.showSuccess("Removed from Favorites"),
                        error -> view.showError("Failed to remove favorite: " + error.getMessage())
                );
        compositeDisposable.add(d);
    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
    }
}
