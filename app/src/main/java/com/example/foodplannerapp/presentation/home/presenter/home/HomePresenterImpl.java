package com.example.foodplannerapp.presentation.home.presenter.home;

import com.example.foodplannerapp.data.datasources.user.UserPreferenceDataSource;
import com.example.foodplannerapp.data.model.meal.Meal;
import com.example.foodplannerapp.data.reposetories.meals.MealsRepository;
import com.example.foodplannerapp.presentation.home.view.home.HomeView;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomePresenterImpl implements HomePresenter {
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Disposable intervalDisposable;
    private final MealsRepository mealsRepository;
    private final HomeView view;
    @Inject
    UserPreferenceDataSource userPref;

    @Override
    public boolean isGuest() {
        return userPref.isGuest();
    }

    @Override
    public String getUsername() {
        return userPref.getUsername();
    }

    @Inject
    public HomePresenterImpl(MealsRepository mealsRepository, HomeView view) {
        this.mealsRepository = mealsRepository;
        this.view = view;
    }

    @Override
    public void observeRandomMeal() {
        view.showRandomMealShimmer();
        Disposable d = mealsRepository.getRandomMeal()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            view.showRandomMeal(response.getMeals());
                            view.hideRandomMealShimmer();
                        },
                        error -> {
                            view.hideRandomMealShimmer();
                            view.showError(error.getMessage());
                        }
                );
        compositeDisposable.add(d);
    }

    @Override
    public void observeCarouselMeals() {
        view.showCarouselShimmer();

        Disposable d = mealsRepository.getRandomMealsBatch(5)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        meals -> {
                            view.showCarouselMeals(meals);
                            view.hideCarouselShimmer();
                            startAutoScroll(); // Start scrolling once data is loaded!
                        },
                        error -> {
                            view.hideCarouselShimmer();
                            view.showError(error.getMessage());
                        }
                );
        compositeDisposable.add(d);
    }

    @Override
    public void startAutoScroll() {
        stopAutoScroll();

        intervalDisposable = Observable.interval(3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> view.moveToNextCarouselItem());
        compositeDisposable.add(intervalDisposable);
    }
    @Override
    public void stopAutoScroll() {
        if (intervalDisposable != null && !intervalDisposable.isDisposed()) {
            intervalDisposable.dispose();
            compositeDisposable.remove(intervalDisposable);
        }
    }

    @Override
    public void onRandomMealClick() {
        view.showProgressbar();
        compositeDisposable.add(
                Completable.timer(
                        100,
                        TimeUnit.MILLISECONDS
                ).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doFinally(view::hideProgressbar)
                        .subscribe(
                                view::navigateToMealDetailsFragment,
                                (throwable -> view.showError(throwable.getMessage()))
                        )
        );
    }

    @Override
    public void onCarouselMealClick(Meal meal) {
        view.showProgressbar();
        compositeDisposable.add(
                Completable.timer(
                                100,
                                TimeUnit.MILLISECONDS
                        ).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doFinally(view::hideProgressbar)
                        .subscribe(
                                () -> view.navigateToMealDetailsWithId(meal.getIdMeal()),
                                (throwable -> view.showError(throwable.getMessage()))
                        )
        );
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
        stopAutoScroll();
        compositeDisposable.clear();
        compositeDisposable.dispose();
    }
}
