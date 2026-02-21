package com.example.foodplannerapp.presentation.meals.presenter.meals_search;

import com.example.foodplannerapp.data.model.meal.Meal;
import com.example.foodplannerapp.data.reposetories.meals.MealsRepository;
import com.example.foodplannerapp.presentation.meals.view.meals_search.SearchView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class SearchPresenterImpl implements SearchPresenter {
    private final MealsRepository repository;
    private final SearchView view;
    private boolean searchByName = true; // Default
    private boolean searchByCategory = false;
    private boolean searchByArea = false;
    private boolean searchByIngredient = false;
    private final CompositeDisposable disposables = new CompositeDisposable();

    private final PublishSubject<String> searchSubject = PublishSubject.create();

    private MealsRepository.SearchType currentType = MealsRepository.SearchType.NAME;

    @Inject
    public SearchPresenterImpl(MealsRepository repository, SearchView view) {
        this.repository = repository;
        this.view = view;
        setupSearchDebounce();
    }

    private void setupSearchDebounce() {
        disposables.add(
                searchSubject
                        .debounce(500, TimeUnit.MILLISECONDS)
                        .distinctUntilChanged()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                this::executeSearchRequests,
                                error -> view.showError("Search error occurred.")
                        )
        );
    }

    @Override
    public void search(String query) {
        if (query == null || query.trim().isEmpty()) {
            view.showInitialState();
            return;
        }
        searchSubject.onNext(query.trim());
    }
    private void executeSearchRequests(String query) {
        List<Single<List<Meal>>> searchRequests = new ArrayList<>();

        if (searchByName) searchRequests.add(repository.searchMeals(query, MealsRepository.SearchType.NAME));
        if (searchByCategory) searchRequests.add(repository.searchMeals(query, MealsRepository.SearchType.CATEGORY));
        if (searchByArea) searchRequests.add(repository.searchMeals(query, MealsRepository.SearchType.AREA));
        if (searchByIngredient) searchRequests.add(repository.searchMeals(query, MealsRepository.SearchType.INGREDIENT));

        if (searchRequests.isEmpty()) {
            view.showInitialState();
            return;
        }

        Disposable disposable = Observable.fromIterable(searchRequests)
                .flatMapSingle(request -> request.onErrorReturnItem(new ArrayList<>()))
                .flatMapIterable(list -> list)
                .distinct(Meal::getIdMeal)
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        meals -> {
                            if (meals.isEmpty()) {
                                view.showNotFoundState();
                            } else {
                                view.showSearchResults(meals);
                            }
                        },
                        error -> view.showError(error.getMessage())
                );

        disposables.add(disposable);
    }

    @Override
    public void setSearchType(MealsRepository.SearchType type) {
        this.currentType = type;
    }

    @Override
    public void updateActiveFilters(boolean isName, boolean isCategory, boolean isArea, boolean isIngredient) {
        this.searchByName = isName;
        this.searchByCategory = isCategory;
        this.searchByArea = isArea;
        this.searchByIngredient = isIngredient;
    }

    @Override
    public void onDestroy() {
        disposables.clear();
    }
}