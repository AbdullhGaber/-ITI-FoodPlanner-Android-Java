package com.example.foodplannerapp.presentation.favorites.view;

import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
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
import com.example.foodplannerapp.presentation.utils.Dialogs;
import com.example.foodplannerapp.presentation.utils.Dialogs.WarningStrategy;
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
    public void showError(String title, String message) {
        Dialogs.show(
                requireContext(),
                new Dialogs.ErrorStrategy(),
                title,
                message,
                "Ok",
                "",
                new Dialogs.OnDialogActionListener() {
                    @Override
                    public void onPositiveClick(Dialog dialog) {
                        dialog.dismiss();
                    }
                    @Override
                    public void onNegativeClick(Dialog dialog) {
                        dialog.dismiss();
                    }
                }
        );
    }

    @Override
    public void showUndoMealSnackBar(MealEntity deletedMeal) {
        Snackbar snackbar = Snackbar.make(binding.getRoot(), "Meal removed from favorites", Snackbar.LENGTH_LONG);

        snackbar.setAction("UNDO", v -> {
            favoritePresenter.insertMeal(deletedMeal);
        });

        snackbar.setActionTextColor(ContextCompat.getColor(requireContext(),R.color.green_header));
        snackbar.show();
    }

    @Override
    public void showRestoredMealSnackBar() {
        Snackbar snackbar = Snackbar.make(binding.getRoot(), "Meal Restored to favorites", Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(ContextCompat.getColor(requireContext(),R.color.green_header));
        snackbar.show();
    }


    @Override
    public void onMealClick(MealEntity meal) {
        FavoriteFragmentDirections.ActionFavoriteFragmentToMealDetailsFragment action =
                FavoriteFragmentDirections.actionFavoriteFragmentToMealDetailsFragment(meal.getIdMeal());
        Navigation.findNavController(binding.getRoot()).navigate(action);
    }

    @Override
    public void onDeleteClick(MealEntity meal) {
        Dialogs.show(
                requireContext(),
                new WarningStrategy(),
                "Remove Favorite?",
                "Are you sure you want to remove " + meal.getStrMeal() + " from your favorites?",
                "Yes, Remove",
                "Cancel",
                new Dialogs.OnDialogActionListener() {
                    @Override
                    public void onPositiveClick(Dialog dialog) {
                        dialog.dismiss();
                        favoritePresenter.deleteMeal(meal);
                    }

                    @Override
                    public void onNegativeClick(Dialog dialog) {
                        dialog.dismiss();
                    }
                }
        );
    }

}