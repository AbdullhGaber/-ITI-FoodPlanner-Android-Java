package com.example.foodplannerapp.presentation.meals.presenter.meals_search;

import com.example.foodplannerapp.data.reposetories.meals.MealsRepository;
import com.example.foodplannerapp.presentation.meals.view.meals_search.SearchView;

import java.util.concurrent.TimeUnit;
import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class SearchPresenterImpl implements SearchPresenter {
    private final MealsRepository repository;
    private final SearchView view;
    private final CompositeDisposable disposables = new CompositeDisposable();

    private final PublishSubject<String> searchSubject = PublishSubject.create();

    private MealsRepository.SearchType currentType = MealsRepository.SearchType.NAME;

    @Inject
    public SearchPresenterImpl(MealsRepository repository, SearchView view) {
        this.repository = repository;
        this.view = view;
        setupSearchObserver();
    }

    private void setupSearchObserver() {
        disposables.add(searchSubject
                .debounce(500, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .filter(text -> !text.isEmpty())
                .switchMapSingle(query ->
                        repository.searchMeals(query, currentType)
                                .subscribeOn(Schedulers.io())
                                .onErrorReturnItem(java.util.Collections.emptyList())
                )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        meals -> {
                            if (meals.isEmpty()) {
                                view.showError("No meals found");
                                view.showSearchResults(java.util.Collections.emptyList());
                            } else {
                                view.showSearchResults(meals);
                            }
                        },
                        error -> view.showError(error.getMessage())
                )
        );
    }

    @Override
    public void search(String query) {
        searchSubject.onNext(query);
    }

    @Override
    public void setSearchType(MealsRepository.SearchType type) {
        this.currentType = type;
    }

    @Override
    public void onDestroy() {
        disposables.clear();
    }
}