package com.example.foodplannerapp.presentation.favorites.presenter;

import com.example.foodplannerapp.data.db.meals.entities.MealEntity;
import com.example.foodplannerapp.data.reposetories.meals.MealsRepository;
import com.example.foodplannerapp.presentation.favorites.view.FavoriteView;
import javax.inject.Inject;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
public class FavoritePresenterImpl implements FavoritePresenter{
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final MealsRepository mealsRepository;
    private final FavoriteView view;

    @Inject
    public FavoritePresenterImpl(MealsRepository mealsRepository, FavoriteView view) {
        this.mealsRepository = mealsRepository;
        this.view = view;
    }

    @Override
    public void getFavMeals() {
        Disposable d = mealsRepository.getFavMeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        view::showFavoriteMeals,
                        throwable -> view.showError("Something went wrong!", throwable.getLocalizedMessage())
                );
        compositeDisposable.add(d);
    }


    @Override
    public void deleteMeal(MealEntity meal) {
        Disposable d = mealsRepository.removeFavoriteMeal(meal.getIdMeal())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> view.showUndoMealSnackBar(meal),
                        throwable -> view.showError("Something went wrong!", throwable.getLocalizedMessage())
                );

        compositeDisposable.add(d);
    }

    @Override
    public void insertMeal(MealEntity meal) {
        Disposable d = mealsRepository.insertMeal(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        view::showRestoredMealSnackBar,
                        throwable -> view.showError("Something went wrong!", throwable.getLocalizedMessage())
                );

        compositeDisposable.add(d);
    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
        compositeDisposable.dispose();
    }
}
