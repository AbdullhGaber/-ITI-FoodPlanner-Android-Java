package com.example.foodplannerapp.presentation.home.view.home;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodplannerapp.R;
import com.example.foodplannerapp.data.datasources.user.UserPreferenceDataSource;
import com.example.foodplannerapp.data.model.meal.Meal;
import com.example.foodplannerapp.data.model.meal_area.Area;
import com.example.foodplannerapp.data.model.meal_category.Category;
import com.example.foodplannerapp.databinding.FragmentHomeBinding;
import com.example.foodplannerapp.presentation.home.presenter.home.HomePresenter;
import com.example.foodplannerapp.presentation.home.view.adapters.AreaAdapter;
import com.example.foodplannerapp.presentation.home.view.adapters.CategoryAdapter;
import com.example.foodplannerapp.presentation.utils.ShimmerUtil;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFragment extends Fragment implements HomeView{
    @Inject
    HomePresenter homePresenter;
    @Inject
    UserPreferenceDataSource userPref;
    FragmentHomeBinding binding;
    AreaAdapter areaAdapter;
    CategoryAdapter categoryAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        setUpRvAdapters();
        observeData();
    }

    private void observeData() {
        if(userPref.isGuest())
        {
            hideAreaShimmer();
        }else{
            homePresenter.observeAllAreas();
        }

        homePresenter.observeAllCategories();
        homePresenter.observeRandomMeal();
    }

    private void setUpRvAdapters() {
        areaAdapter = new AreaAdapter();
        categoryAdapter = new CategoryAdapter();
        if(!userPref.isGuest())
            binding.recyclerAreas.setAdapter(areaAdapter);

        binding.recyclerCategories.setAdapter(categoryAdapter);
    }

    private void initViews() {
        if(userPref.isGuest()){
            binding.tvIngredientsTitle.setVisibility(GONE);
            binding.recyclerAreas.setVisibility(GONE);
        }

        binding.tvSeeAllCountries.setOnClickListener(
                v -> Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_allAreasFragment)
        );

        binding.tvSeeAllCategories.setOnClickListener(
                v -> Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_allCategoriesFragment)
        );

        binding.ivSearch.setOnClickListener(
                (v) -> {
                    Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_searchFragment);
                }
        );
    }
    @Override
    public void showRandomMeal(List<Meal> meals) {
        Meal meal = meals.get(0);
        binding.mealDayContainer.setOnClickListener(
                (v) ->{
                    HomeFragmentDirections.ActionHomeFragmentToMealDetailsFragment action =
                            HomeFragmentDirections.actionHomeFragmentToMealDetailsFragment(meal.getIdMeal());
                    Navigation.findNavController(v).navigate(action);
                }
        );
        ShimmerUtil.addShimmerToImage(requireContext(), meal.getStrMealThumb(), binding.imgMealDay);
        binding.tvMealDayName.setText(meal.getStrMeal());
        binding.tvMealDayDesc.setText(String.format("%s • %s", meal.getStrArea(), meal.getStrCategory()));
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
        binding.areaShimmer.showShimmer(true);
        binding.areaShimmer.setVisibility(VISIBLE);
    }

    @Override
    public void hideAreaShimmer() {
        binding.areaShimmer.showShimmer(false);
        binding.areaShimmer.setVisibility(GONE);
    }

    @Override
    public void showCategoryShimmer() {
        binding.categoryShimmer.showShimmer(true);
        binding.categoryShimmer.setVisibility(VISIBLE);
    }

    @Override
    public void hideRandomMealShimmer() {
        binding.mealShimmer.showShimmer(false);
        binding.mealShimmer.setVisibility(GONE);
        binding.cardMealOfDay.setVisibility(VISIBLE);
    }

    @Override
    public void showRandomMealShimmer() {
        binding.mealShimmer.showShimmer(true);
        binding.mealShimmer.setVisibility(VISIBLE);
    }

    @Override
    public void hideCategoryShimmer() {
        binding.categoryShimmer.showShimmer(false);
        binding.categoryShimmer.setVisibility(GONE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        homePresenter.onDestroy();
        homePresenter = null;
    }
}