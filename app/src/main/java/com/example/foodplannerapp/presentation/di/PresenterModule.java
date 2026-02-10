package com.example.foodplannerapp.presentation.di;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.foodplannerapp.presentation.auth.login.presenter.LoginPresenter;
import com.example.foodplannerapp.presentation.auth.login.presenter.LoginPresenterImpl;
import com.example.foodplannerapp.presentation.auth.login.views.LoginView;
import com.example.foodplannerapp.presentation.auth.register.presenter.RegisterPresenter;
import com.example.foodplannerapp.presentation.auth.register.presenter.RegisterPresenterImpl;
import com.example.foodplannerapp.presentation.auth.register.views.RegisterView;
import com.example.foodplannerapp.presentation.favorites.presenter.FavoritePresenter;
import com.example.foodplannerapp.presentation.favorites.presenter.FavoritePresenterImpl;
import com.example.foodplannerapp.presentation.favorites.view.FavoriteView;
import com.example.foodplannerapp.presentation.home.presenter.areas.AllAreasPresenter;
import com.example.foodplannerapp.presentation.home.presenter.areas.AllAreasPresenterImpl;
import com.example.foodplannerapp.presentation.home.presenter.categories.AllCategoriesPresenter;
import com.example.foodplannerapp.presentation.home.presenter.categories.AllCategoriesPresenterImpl;
import com.example.foodplannerapp.presentation.home.presenter.home.HomePresenter;
import com.example.foodplannerapp.presentation.home.presenter.home.HomePresenterImpl;
import com.example.foodplannerapp.presentation.home.view.areas.AllAreasView;
import com.example.foodplannerapp.presentation.home.view.categories.AllCategoriesView;
import com.example.foodplannerapp.presentation.home.view.home.HomeView;
import com.example.foodplannerapp.presentation.meals.presenter.meal_details.MealDetailsPresenter;
import com.example.foodplannerapp.presentation.meals.presenter.meal_details.MealDetailsPresenterImpl;
import com.example.foodplannerapp.presentation.meals.presenter.meals_search.SearchPresenter;
import com.example.foodplannerapp.presentation.meals.presenter.meals_search.SearchPresenterImpl;
import com.example.foodplannerapp.presentation.meals.view.meals_details.MealDetailsView;
import com.example.foodplannerapp.presentation.meals.view.meals_search.SearchView;

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
    abstract LoginPresenter getLoginPresenter(LoginPresenterImpl loginPresenter);

    @NonNull
    @Contract("null -> fail")
    @Provides
    public static LoginView provideLoginView(Fragment fragment) {
        if (fragment instanceof LoginView) {
            return (LoginView) fragment;
        }
        throw new IllegalStateException("Fragment must implement LoginView");
    }

    @Binds
    abstract RegisterPresenter getRegisterPresenter(RegisterPresenterImpl registerPresenter);

    @NonNull
    @Contract("null -> fail")
    @Provides
    public static RegisterView provideRegisterView(Fragment fragment) {
        if (fragment instanceof RegisterView) {
            return (RegisterView) fragment;
        }
        throw new IllegalStateException("Fragment must implement RegisterView");
    }

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

    @Binds
    abstract AllCategoriesPresenter getAllCategoriesPresenter(AllCategoriesPresenterImpl homePresenter);

    @NonNull
    @Contract("null -> fail")
    @Provides
    public static AllCategoriesView provideAllCategoriesView(Fragment fragment) {
        if (fragment instanceof AllCategoriesView) {
            return (AllCategoriesView) fragment;
        }
        throw new IllegalStateException("Fragment must implement AllCategoriesView");
    }

    @Binds
    abstract FavoritePresenter getFavoritePresenter(FavoritePresenterImpl favoritePresenter);

    @NonNull
    @Contract("null -> fail")
    @Provides
    public static FavoriteView provideFavoriteView(Fragment fragment) {
        if (fragment instanceof FavoriteView) {
            return (FavoriteView) fragment;
        }
        throw new IllegalStateException("Fragment must implement FavoriteView");
    }

    @Binds
    abstract MealDetailsPresenter getMealDetailsPresenter(MealDetailsPresenterImpl mealDetailsPresenter);

    @NonNull
    @Contract("null -> fail")
    @Provides
    public static MealDetailsView provideMealDetailsView(Fragment fragment) {
        if (fragment instanceof MealDetailsView) {
            return (MealDetailsView) fragment;
        }
        throw new IllegalStateException("Fragment must implement MealDetailsView");
    }

    @Binds
    abstract SearchPresenter bindSearchPresenter(SearchPresenterImpl impl);

    @NonNull
    @Contract("null -> fail")
    @Provides
    public static SearchView provideSearchView(Fragment fragment) {
        if (fragment instanceof SearchView) {
            return (SearchView) fragment;
        }
        throw new IllegalStateException("Fragment must implement SearchView");
    }
}
