package com.example.foodplannerapp.presentation.di;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.foodplannerapp.presentation.home.presenter.areas.AllAreasPresenter;
import com.example.foodplannerapp.presentation.home.presenter.areas.AllAreasPresenterImpl;
import com.example.foodplannerapp.presentation.home.presenter.home.HomePresenter;
import com.example.foodplannerapp.presentation.home.presenter.home.HomePresenterImpl;
import com.example.foodplannerapp.presentation.home.view.areas.AllAreasView;
import com.example.foodplannerapp.presentation.home.view.home.HomeView;
import org.jetbrains.annotations.Contract;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.FragmentComponent;

@Module
@InstallIn(FragmentComponent.class)
public abstract class PresenterModule {
    @Binds
    abstract HomePresenter getHomePresenter(HomePresenterImpl homePresenter);

    @NonNull
    @Contract("null -> fail")
    @Provides
    public static HomeView provideHomeView(Fragment fragment) {
        if (fragment instanceof HomeView) {
            return (HomeView) fragment;
        }
        throw new IllegalStateException("Fragment must implement HomeView");
    }

    @Binds
    abstract AllAreasPresenter getAllAreasPresenter(AllAreasPresenterImpl homePresenter);

    @NonNull
    @Contract("null -> fail")
    @Provides
    public static AllAreasView provideAllAreasView(Fragment fragment) {
        if (fragment instanceof AllAreasView) {
            return (AllAreasView) fragment;
        }
        throw new IllegalStateException("Fragment must implement AllAreasView");
    }
}
