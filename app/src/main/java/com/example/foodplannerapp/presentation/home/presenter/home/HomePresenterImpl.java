package com.example.foodplannerapp.presentation.home.presenter.home;

import com.example.foodplannerapp.data.reposetories.meals.MealsRepository;
import com.example.foodplannerapp.presentation.home.view.home.HomeView;
import javax.inject.Inject;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class HomePresenterImpl implements HomePresenter {
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final MealsRepository mealsRepository;
    private final HomeView view;

    @Inject
    public HomePresenterImpl(MealsRepository mealsRepository, HomeView view) {
        this.mealsRepository = mealsRepository;
        this.view = view;
    }

    @Override
    public void getRandomMeal() {
        mealsRepository.getRandomMeal();
    }

    @Override
    public void observeAllCategories() {
        view.showCategoryShimmer();
        Disposable d = mealsRepository.getAllCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            view.showCategories(response.getCategories());
                            view.hideCategoryShimmer();
                        },
                        error -> {
                            view.hideCategoryShimmer();
                            view.showError(error.getMessage());
                        }
                );
        compositeDisposable.add(d);
    }

    @Override
    public void observeAllAreas() {
        view.showAreaShimmer();
        Disposable d = mealsRepository.getAllAreas()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            view.showAreas(response.getAreas());
                            view.hideAreaShimmer();
                        },
                        error -> {
                            view.hideAreaShimmer();
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
