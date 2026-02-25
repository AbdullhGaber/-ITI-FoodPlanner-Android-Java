package com.example.foodplannerapp.presentation.profile.presenter;

import com.example.foodplannerapp.data.datasources.user.UserPreferenceDataSource;
import com.example.foodplannerapp.data.reposetories.meals.MealsRepository;
import com.example.foodplannerapp.presentation.profile.view.ProfileView;
import com.google.firebase.auth.FirebaseAuth;
import javax.inject.Inject;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProfilePresenterImpl implements ProfilePresenter {
    @Inject
    MealsRepository repository;
    private final ProfileView view;
    @Inject
    UserPreferenceDataSource userPrefs;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Inject
    public ProfilePresenterImpl(MealsRepository repository, ProfileView view) {
        this.repository = repository;
        this.view = view;
    }
    @Override
    public void observeMealCounts() {
        Disposable favDisposable = repository.getFavoritesCount()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        view::showFavoritesCount,
                        error -> view.showError(error.getMessage())
                );

        Disposable planDisposable = repository.getPlansCount()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        view::showPlansCount,
                        error -> view.showError(error.getMessage())
                );

        compositeDisposable.addAll(favDisposable, planDisposable);
    }

    @Override
    public void observeUserData() {
        view.displayUserData(userPrefs.getUsername(),userPrefs.getUserEmail());
    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
    }

    @Override
    public void logout() {
        FirebaseAuth.getInstance().signOut();
        Disposable d = repository.deleteAllMeals()
                        .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(
                                                () ->{
                                                    userPrefs.setLoginState(false, null, null, "");
                                                    view.navigateToLogin();
                                                }
                                        );
    }
}