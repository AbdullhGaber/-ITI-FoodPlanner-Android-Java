package com.example.foodplannerapp.presentation.meals.presenter.meal_details;

import android.content.Context;

import com.example.foodplannerapp.data.datasources.user.UserPreferenceDataSource;
import com.example.foodplannerapp.data.db.meals.entities.MealEntity;
import com.example.foodplannerapp.data.model.meal.Meal;
import com.example.foodplannerapp.data.reposetories.meals.MealsRepository;
import com.example.foodplannerapp.data.utils.MealMapper;
import com.example.foodplannerapp.presentation.meals.view.meals_details.MealDetailsView;
import javax.inject.Inject;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealDetailsPresenterImpl implements MealDetailsPresenter{
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final MealsRepository mealsRepository;
    private final MealDetailsView view;
    @Inject
    UserPreferenceDataSource userPrefs;

    @Override
    public boolean isGuest() {
        return userPrefs.isGuest();
    }

    @Override
    public void removeUserLoginState() {
        userPrefs.saveGuest(false);
        userPrefs.setLoginState(false, "", "");
    }

    @Inject
    public MealDetailsPresenterImpl(MealsRepository mealsRepository, MealDetailsView view) {
        this.mealsRepository = mealsRepository;
        this.view = view;
    }

    @Override
    public void getMealDetails(String mealId) {
        Disposable d = mealsRepository.getMealDetails(mealId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        view::showMealDetails,
                        error -> {
                            view.hideLoading();
                            view.showError(error.getMessage());
                        }
                );
        compositeDisposable.add(d);
    }

    @Override
    public void addToFavorites(Meal currentMeal, Context context) {
        compositeDisposable.add(
                Observable.just(currentMeal)
                        .subscribeOn(Schedulers.io())
                        .map(meal -> MealMapper.toEntity(meal, context))
                        .flatMapCompletable(
                                (mealEntity) -> {
                                    mealEntity.setFav(true);
                                    return mealsRepository.insertMeal(mealEntity);
                                }
                        )
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> view.showSuccess("Added to favorites"),
                                throwable -> view.showError("Failed to add to favorites")
                        )
        );
    }

    @Override
    public void removeFromFavorites(Meal meal) {
        if (meal == null) return;

        MealEntity dbMeal = new MealEntity();
        dbMeal.setIdMeal(meal.getIdMeal());
        dbMeal.setStrMeal(meal.getStrMeal());
        Disposable d = mealsRepository.removeFavoriteMeal(meal.getIdMeal())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                            view.showSuccess("Removed from Favorites");
                            if(meal.getDayOfWeek() == null){
                                mealsRepository.deleteMeal(dbMeal);
                            }
                        },
                        error -> view.showError("Failed to remove favorite: " + error.getMessage())
                );
        compositeDisposable.add(d);
    }

    @Override
    public void addToPlan(Meal meal, Context context) {
        view.showLoading();
        Disposable d = Observable.just(meal)
                .subscribeOn(Schedulers.io())
                .map(
                        (lMeal) -> MealMapper.toEntity(lMeal,context)
                )
                .flatMapCompletable(mealsRepository::insertMeal
                )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                            view.hideLoading();
                            view.showSuccess("Meal added in " + meal.getDayOfWeek());
                        },
                        error -> view.showError(error.getMessage())
                );
        compositeDisposable.add(d);
    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
    }
}
