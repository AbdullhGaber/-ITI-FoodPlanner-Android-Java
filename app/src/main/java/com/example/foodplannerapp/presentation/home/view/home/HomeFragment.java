package com.example.foodplannerapp.presentation.home.view.home;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.foodplannerapp.R;
import com.example.foodplannerapp.data.model.meal_area.Area;
import com.example.foodplannerapp.data.model.meal_category.Category;
import com.example.foodplannerapp.presentation.home.presenter.home.HomePresenter;
import com.example.foodplannerapp.presentation.home.view.adapters.AreaAdapter;
import com.example.foodplannerapp.presentation.home.view.adapters.CategoryAdapter;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFragment extends Fragment implements HomeView{
    @Inject
    HomePresenter homePresenter;
    RecyclerView areasRv;
    AreaAdapter areaAdapter;
    RecyclerView categoriesRv;
    CategoryAdapter categoryAdapter;
    ShimmerFrameLayout areaShimmer;
    ShimmerFrameLayout categoryShimmer;
    TextView seeAllCountriesTv;
    TextView seeAllCategoriesTv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        setUpRvAdapters();
        observeData();
    }

    private void observeData() {
        homePresenter.observeAllAreas();
        homePresenter.observeAllCategories();
    }

    private void setUpRvAdapters() {
        areaAdapter = new AreaAdapter();
        categoryAdapter = new CategoryAdapter();
        areasRv.setAdapter(areaAdapter);
        categoriesRv.setAdapter(categoryAdapter);
    }

    private void initViews() {
        areasRv = requireView().findViewById(R.id.recycler_areas);
        categoriesRv = requireView().findViewById(R.id.recycler_categories);
        areaShimmer = requireView().findViewById(R.id.areaShimmer);
        categoryShimmer = requireView().findViewById(R.id.categoryShimmer);
        seeAllCountriesTv = requireView().findViewById(R.id.tv_see_all_countries);
        seeAllCategoriesTv = requireView().findViewById(R.id.tv_see_all_categories);

        seeAllCountriesTv.setOnClickListener(
                v -> Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_allAreasFragment)
        );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        homePresenter.onDestroy();
        homePresenter = null;
    }

    @Override
    public void showAreas(List<Area> areas) {
        areaAdapter.submitList(areas);
    }

    @Override
    public void showCategories(List<Category> categories) {
        categoryAdapter.submitList(categories);
    }

    @Override
    public void showError(String msg) {
        Snackbar.make(requireView(),msg,Snackbar.ANIMATION_MODE_FADE).show();
    }

    @Override
    public void showAreaShimmer() {
        areaShimmer.showShimmer(true);
        areaShimmer.setVisibility(VISIBLE);
    }

    @Override
    public void hideAreaShimmer() {
        areaShimmer.showShimmer(false);
        areaShimmer.setVisibility(GONE);
    }

    @Override
    public void showCategoryShimmer() {
        categoryShimmer.showShimmer(true);
        categoryShimmer.setVisibility(VISIBLE);
    }

    @Override
    public void hideCategoryShimmer() {
        categoryShimmer.showShimmer(false);
        categoryShimmer.setVisibility(GONE);
    }
}