package com.example.foodplannerapp.presentation.profile.presenter;

import com.google.firebase.auth.FirebaseAuth;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public interface ProfilePresenter {
     void backup();

     void restore();

     void logout();
     void onDestroy();
}