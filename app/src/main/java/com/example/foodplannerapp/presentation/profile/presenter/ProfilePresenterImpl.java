package com.example.foodplannerapp.presentation.profile.presenter;

import com.example.foodplannerapp.data.datasources.user.UserPreferenceDataSource;
import com.example.foodplannerapp.data.reposetories.backup.BackupRepository;
import com.example.foodplannerapp.presentation.profile.view.ProfileView;
import com.google.firebase.auth.FirebaseAuth;
import javax.inject.Inject;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProfilePresenterImpl implements ProfilePresenter {
    @Inject
    BackupRepository repository;
    private final ProfileView view;
    private final UserPreferenceDataSource userPrefs;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Inject
    public ProfilePresenterImpl(BackupRepository repository, ProfileView view, UserPreferenceDataSource userPrefs) {
        this.repository = repository;
        this.view = view;
        this.userPrefs = userPrefs;
    }
    @Override
    public void backup() {
        Disposable d = repository.backupData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                    () -> view.showSuccess("Backup Completed"),
                    err -> view.showError(err.getMessage())
            );
        compositeDisposable.add(d);
    }
    @Override
    public void restore() {
        Disposable d = repository.restoreData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(() -> view.showSuccess("Data Restored"),
                    err -> view.showError(err.getMessage())
            );

        compositeDisposable.add(d);
    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
    }

    @Override
    public void logout() {
        FirebaseAuth.getInstance().signOut();
        userPrefs.setLoginState(false, null);
        view.navigateToLogin();
    }
}