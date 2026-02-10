package com.example.foodplannerapp.presentation.planner.presenter;

import com.example.foodplannerapp.data.reposetories.meals.MealsRepository;
import com.example.foodplannerapp.data.db.meals.entities.PlanMeal;
import com.example.foodplannerapp.presentation.planner.view.PlannerView;

import javax.inject.Inject;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PlannerPresenterImpl implements PlannerPresenter {

    private final MealsRepository repository;
    private final PlannerView view;
    private final CompositeDisposable disposables = new CompositeDisposable();

    @Inject
    public PlannerPresenterImpl(MealsRepository repository, PlannerView view) {
        this.repository = repository;
        this.view = view;
    }

    @Override
    public void getMealsForDay(String day) {
        Disposable d = repository.getPlansByDay(day)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        plans -> view.showPlannedMeals(plans),
                        error -> view.showError(error.getMessage())
                );
        disposables.add(d);
    }

    @Override
    public void deletePlan(PlanMeal plan) {
        Disposable d = repository.deletePlan(plan)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> view.showSuccess("Meal removed from plan"),
                        error -> view.showError(error.getMessage())
                );
        disposables.add(d);
    }

    @Override
    public void onDestroy() {
        disposables.clear();
    }
}