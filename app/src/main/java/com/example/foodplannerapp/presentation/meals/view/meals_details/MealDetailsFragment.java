package com.example.foodplannerapp.presentation.meals.view.meals_details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.bumptech.glide.Glide;
import com.example.foodplannerapp.R;
import com.example.foodplannerapp.data.model.meal.Meal;
import com.example.foodplannerapp.databinding.FragmentMealDetailsBinding;
import com.example.foodplannerapp.presentation.meals.presenter.meal_details.MealDetailsPresenter;
import com.example.foodplannerapp.presentation.meals.view.adapters.IngredientsAdapter;
import javax.inject.Inject;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MealDetailsFragment extends Fragment implements MealDetailsView {

    @Inject
    MealDetailsPresenter presenter;

    private FragmentMealDetailsBinding binding;
    private IngredientsAdapter ingredientsAdapter;
    private Meal currentMeal;
    private boolean isFavorite = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMealDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            String mealId = MealDetailsFragmentArgs.fromBundle(getArguments()).getMealId();
            presenter.getMealDetails(mealId);
        }

        setupIngredients();

        binding.btnFavorite.setOnClickListener(v -> toggleFavorite());
        binding.btnBack.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());
        binding.btnCalendar.setOnClickListener(v -> showDayPicker());
    }

    @Override
    public void showMealDetails(Meal meal) {
        this.currentMeal = meal;

        if (meal.getLocalImageBytes() != null && meal.getLocalImageBytes().length > 0) {
            isFavorite = true;
        } else {
            isFavorite = false;
        }
        updateFavoriteIcon();

        setupUI();
        setupVideo();

        ingredientsAdapter.submitList(meal.getIngredientsList());
    }

    private void setupUI() {
        binding.tvMealName.setText(currentMeal.getStrMeal());
        binding.tvArea.setText(currentMeal.getStrArea());
        binding.tvInstructions.setText(currentMeal.getStrInstructions());
        
        if (currentMeal.getLocalImageBytes() != null && currentMeal.getLocalImageBytes().length > 0) {
            Glide.with(this).load(currentMeal.getLocalImageBytes()).into(binding.ivMealImage);
        } else {
            Glide.with(this).load(currentMeal.getStrMealThumb()).into(binding.ivMealImage);
        }
    }

    private void setupIngredients() {
        ingredientsAdapter = new IngredientsAdapter();
        binding.rvIngredients.setAdapter(ingredientsAdapter);
    }

    private void toggleFavorite() {
        if (currentMeal == null) return;

        isFavorite = !isFavorite;
        updateFavoriteIcon();

        if (isFavorite) {
            presenter.addToFavorites(currentMeal, requireContext());
        } else {
            presenter.removeFromFavorites(currentMeal);
        }
    }

    private void updateFavoriteIcon() {
        if (isFavorite) {
            binding.btnFavorite.setImageResource(R.drawable.ic_heart_filled);
            binding.btnFavorite.setColorFilter(null);
        } else {
            binding.btnFavorite.setImageResource(R.drawable.ic_heart_unfilled);
            binding.btnFavorite.setColorFilter(getResources().getColor(android.R.color.white));
        }
    }

    private void setupVideo() {
        String videoUrl = currentMeal.getStrYoutube();
        if (videoUrl != null && !videoUrl.isEmpty()) {
            String embedUrl = videoUrl.replace("watch?v=", "embed/");
            binding.wvVideo.getSettings().setJavaScriptEnabled(true);
            binding.wvVideo.setWebChromeClient(new WebChromeClient());
            binding.wvVideo.loadUrl(embedUrl);
            binding.videoContainer.setVisibility(View.VISIBLE);
        } else {
            binding.videoContainer.setVisibility(View.GONE);
        }
    }

    private void showDayPicker() {
        final String[] days = {"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};

        new com.google.android.material.dialog.MaterialAlertDialogBuilder(requireContext())
                .setTitle("Choose a Day")
                .setItems(days, (dialog, which) -> {
                    String selectedDay = days[which];
                    saveMealToPlan(selectedDay);
                })
                .show();
    }
    private void saveMealToPlan(String day) {
        if (currentMeal == null) return;

        com.example.foodplannerapp.data.db.meals.entities.PlanMeal plan =
                new com.example.foodplannerapp.data.db.meals.entities.PlanMeal(
                        currentMeal.getIdMeal(),
                        day,
                        currentMeal.getStrMeal(),
                        currentMeal.getStrMealThumb(),
                        currentMeal.getStrArea(),
                        currentMeal.getStrCategory(),
                        currentMeal.getLocalImageBytes()
                );

        presenter.addToPlan(plan);
    }

    @Override
    public void showLoading() {
        // Create a ProgressBar in your XML with id "progressBar"
        // binding.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        // binding.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String msg) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSuccess(String msg) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.onDestroy();
        }
    }
}