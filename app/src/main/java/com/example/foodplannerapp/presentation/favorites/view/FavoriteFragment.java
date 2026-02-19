package com.example.foodplannerapp.presentation.favorites.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodplannerapp.R;
import com.example.foodplannerapp.data.db.meals.entities.MealEntity;
import com.example.foodplannerapp.databinding.FragmentFavoriteBinding;
import com.example.foodplannerapp.presentation.favorites.presenter.FavoritePresenter;
import com.example.foodplannerapp.presentation.favorites.view.adapters.FavoriteMealsAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FavoriteFragment extends Fragment implements FavoriteView, FavoriteMealsAdapter.OnFavoriteClickListener{
    @Inject
    FavoritePresenter favoritePresenter;
    private FavoriteMealsAdapter adapter;
    FragmentFavoriteBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView();
        observeMeals();
    }
    private void setupRecyclerView() {
        adapter = new FavoriteMealsAdapter(this);
        binding.rvFavorites.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvFavorites.setAdapter(adapter);
    }
    private void observeMeals() {
        favoritePresenter.getFavMeals();
    }

    @Override
    public void showFavoriteMeals(List<MealEntity> meals) {
        if (meals == null || meals.isEmpty()) {
            binding.rvFavorites.setVisibility(View.GONE);
            binding.layoutEmpty.emptyStateContainer.setVisibility(View.VISIBLE);

            binding.layoutEmpty.tvEmptyTitle.setText(R.string.no_favorites_yet);
            binding.layoutEmpty.tvEmptySubtitle.setText(R.string.tap_the_heart_icon_on_a_meal_to_save_it_here);

        } else {
            binding.layoutEmpty.emptyStateContainer.setVisibility(View.GONE);
            binding.rvFavorites.setVisibility(View.VISIBLE);

            adapter.submitList(meals);
        }
    }

    @Override
    public void showError(String msg) {
        Snackbar.make(requireView(),msg,Snackbar.ANIMATION_MODE_FADE).show();
    }

    @Override
    public void showSuccess(String msg) {
        Snackbar.make(requireView(),msg,Snackbar.ANIMATION_MODE_FADE).show();
    }

    @Override
    public void onMealClick(MealEntity meal) {
        FavoriteFragmentDirections.ActionFavoriteFragmentToMealDetailsFragment action =
                FavoriteFragmentDirections.actionFavoriteFragmentToMealDetailsFragment(meal.getIdMeal());
        Navigation.findNavController(binding.getRoot()).navigate(action);
    }

    @Override
    public void onDeleteClick(MealEntity meal) {
        favoritePresenter.deleteMeal(meal);
    }
}