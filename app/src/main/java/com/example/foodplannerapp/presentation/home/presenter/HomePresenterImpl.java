package com.example.foodplannerapp.presentation.home.presenter;

import com.example.foodplannerapp.data.reposetories.meals.MealsRepository;
import com.example.foodplannerapp.presentation.home.view.HomeView;
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
    public void observeAllArea() {
        view.showShimmer();
        Disposable d = mealsRepository.getAllAreas()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            view.showAreas(response.getAreas());
                            view.hideShimmer();
                        },
                        error -> {
                            view.showError(error.getMessage());
                            view.hideShimmer();
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
