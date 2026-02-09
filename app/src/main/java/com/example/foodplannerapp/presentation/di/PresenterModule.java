package com.example.foodplannerapp.presentation.di;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.foodplannerapp.presentation.home.presenter.HomePresenter;
import com.example.foodplannerapp.presentation.home.presenter.HomePresenterImpl;
import com.example.foodplannerapp.presentation.home.view.HomeView;
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
}
