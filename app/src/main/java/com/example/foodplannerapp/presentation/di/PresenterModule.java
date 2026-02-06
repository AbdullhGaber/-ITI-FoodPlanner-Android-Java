package com.example.foodplannerapp.presentation.di;

import com.example.foodplannerapp.presentation.home.presenter.HomePresenter;
import com.example.foodplannerapp.presentation.home.presenter.HomePresenterImpl;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.FragmentComponent;

@Module
@InstallIn(FragmentComponent.class)
public interface PresenterModule {
    @Binds
    HomePresenter getHomePresenter(HomePresenterImpl homePresenter);
}
