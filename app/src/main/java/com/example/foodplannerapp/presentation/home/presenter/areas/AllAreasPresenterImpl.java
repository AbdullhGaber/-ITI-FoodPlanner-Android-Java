package com.example.foodplannerapp.presentation.home.presenter.areas;

import com.example.foodplannerapp.data.reposetories.meals.MealsRepository;
import com.example.foodplannerapp.presentation.home.view.areas.AllAreasView;
import com.example.foodplannerapp.presentation.home.view.home.HomeView;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AllAreasPresenterImpl implements AllAreasPresenter{
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final MealsRepository mealsRepository;
    private final AllAreasView view;

    @Inject
    public AllAreasPresenterImpl(MealsRepository mealsRepository, AllAreasView view) {
        this.mealsRepository = mealsRepository;
        this.view = view;
    }

    @Override
    public void observeAllAreas() {
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
                            view.hideShimmer();
                            view.showError(error.getMessage());
                        }
                );
        compositeDisposable.add(d);
    }

    @Override
    public void onDestroy() {
        compositeDisposable.dispose();
        compositeDisposable.clear();
    }
}
