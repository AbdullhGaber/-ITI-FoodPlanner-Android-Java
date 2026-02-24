package com.example.foodplannerapp.presentation.planner.presenter;

import com.example.foodplannerapp.data.datasources.user.UserPreferenceDataSource;
import com.example.foodplannerapp.data.db.meals.entities.MealEntity;
import com.example.foodplannerapp.data.reposetories.meals.MealsRepository;
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
    private Disposable currentDayDisposable;
    @Inject
    UserPreferenceDataSource userPrefs;

    @Inject
    public PlannerPresenterImpl(MealsRepository repository, PlannerView view) {
        this.repository = repository;
        this.view = view;
    }

    @Override
    public void removeUserLoginState() {
        userPrefs.saveGuest(false);
        userPrefs.setLoginState(false, "", "");
    }

    @Override
    public boolean isGuest() {
        return userPrefs.isGuest();
    }

    @Override
    public void getMealsForDay(String day) {
        if (currentDayDisposable != null && !currentDayDisposable.isDisposed()) {
            currentDayDisposable.dispose();
            disposables.remove(currentDayDisposable);
        }

        currentDayDisposable = repository.getPlansByDay(day)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        view::showPlannedMeals,
                        throwable -> view.showError("Something went wrong", throwable.getLocalizedMessage())
                );

        disposables.add(currentDayDisposable);
    }

    @Override
    public void deletePlan(MealEntity meal) {
        Disposable d = repository.removePlanMeal(meal.getIdMeal())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        ()-> view.showUndoPlanSnackBar(meal),
                        throwable -> view.showError("Something went wrong", throwable.getLocalizedMessage())
                );

        disposables.add(d);
    }

    @Override
    public void insertPlan(MealEntity meal) {
        Disposable d = repository.insertMeal(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        view::showRestoredPlanSnackBar,
                        throwable -> view.showError("Something went wrong", throwable.getLocalizedMessage())
                );
        disposables.add(d);
    }

    @Override
    public void onDestroy() {
        disposables.clear();
    }
}