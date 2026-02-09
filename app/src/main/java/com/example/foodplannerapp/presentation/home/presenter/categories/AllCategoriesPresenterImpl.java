package com.example.foodplannerapp.presentation.home.presenter.categories;

import com.example.foodplannerapp.data.reposetories.meals.MealsRepository;
import com.example.foodplannerapp.presentation.home.view.areas.AllAreasView;
import com.example.foodplannerapp.presentation.home.view.categories.AllCategoriesView;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AllCategoriesPresenterImpl implements AllCategoriesPresenter{
    private final AllCategoriesView view;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final MealsRepository mealsRepository;

    @Inject
    public AllCategoriesPresenterImpl(MealsRepository mealsRepository, AllCategoriesView view) {
        this.mealsRepository = mealsRepository;
        this.view = view;
    }
    @Override
    public void observeAllCategories() {
        view.showShimmer();
        Disposable d = mealsRepository.getAllCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            view.showCategories(response.getCategories());
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

    }
}
