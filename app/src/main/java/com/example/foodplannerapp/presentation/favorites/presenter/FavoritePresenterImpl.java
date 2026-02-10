package com.example.foodplannerapp.presentation.favorites.presenter;

import com.example.foodplannerapp.data.db.meals.entities.Meal;
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
                        error -> {
                            view.showError(error.getLocalizedMessage());
                        }
                );
        compositeDisposable.add(d);
    }
    @Override
    public void deleteMeal(Meal meal) {
        Disposable d = mealsRepository.deleteMeal(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                            view.showSuccess("Meal deleted successfully");
                        },
                        error -> {
                            view.showError(error.getMessage());
                        }
                );
        compositeDisposable.add(d);
    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
        compositeDisposable.dispose();
    }
}
