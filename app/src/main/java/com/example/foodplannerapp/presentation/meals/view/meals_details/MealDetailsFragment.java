package com.example.foodplannerapp.presentation.meals.view.meals_details;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.bumptech.glide.Glide;
import com.example.foodplannerapp.R;
import com.example.foodplannerapp.data.datasources.user.UserPreferenceDataSource;
import com.example.foodplannerapp.data.db.meals.entities.PlanMeal;
import com.example.foodplannerapp.data.model.meal.Meal;
import com.example.foodplannerapp.databinding.FragmentMealDetailsBinding;
import com.example.foodplannerapp.presentation.activities.MainActivity;
import com.example.foodplannerapp.presentation.meals.presenter.meal_details.MealDetailsPresenter;
import com.example.foodplannerapp.presentation.meals.view.adapters.IngredientsAdapter;
import javax.inject.Inject;
import dagger.hilt.android.AndroidEntryPoint;

import com.example.foodplannerapp.presentation.meals.view.adapters.InstructionsAdapter;
import com.example.foodplannerapp.presentation.utils.ShimmerUtil;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;

import java.util.ArrayList;
import java.util.List;

@AndroidEntryPoint
public class MealDetailsFragment extends Fragment implements MealDetailsView {
    @Inject
    UserPreferenceDataSource userPrefs;
    @Inject
    MealDetailsPresenter presenter;
    private FragmentMealDetailsBinding binding;
    private IngredientsAdapter ingredientsAdapter;
    private InstructionsAdapter instructionsAdapter; // Declare adapter
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
        updateFavoriteIcon();
        binding.btnFavorite.setOnClickListener(v -> toggleFavorite());
        binding.btnBack.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());
        binding.btnCalendar.setOnClickListener(v -> showDayPicker());
    }

    @Override
    public void showMealDetails(Meal meal) {
        ShimmerUtil.showShimmer(binding.shimmerViewContainer);
        this.currentMeal = meal;
        isFavorite = meal.getLocalImageBytes() != null && meal.getLocalImageBytes().length > 0;

        setupUI();
        setupVideo();
        ShimmerUtil.hideShimmer(binding.shimmerViewContainer);
    }

    private void setupIngredientsAdapter() {
        ingredientsAdapter = new IngredientsAdapter();
        binding.rvIngredients.setAdapter(ingredientsAdapter);
    }
    private void setupInstructionsAdapter() {
        instructionsAdapter = new InstructionsAdapter();
        binding.rvInstructions.setAdapter(instructionsAdapter);
    }

    private void setUpIngredientsRv() {
        ingredientsAdapter.submitList(currentMeal.getIngredientsList());
    }
    private void setUpMealInstructionsRv(){
        String rawInstructions = currentMeal.getStrInstructions();

        if (rawInstructions != null && !rawInstructions.isEmpty()) {
            String[] splitSteps = rawInstructions.split("\\r?\\n");
            List<String> stepList = new ArrayList<>();
            for (String step : splitSteps) {
                if (!step.trim().isEmpty() && !step.toLowerCase().contains("step") && step.length() > 1) {
                    stepList.add(step.trim());
                }
            }
            instructionsAdapter.submitList(stepList);
        }
    }

    private void setupUI() {
        binding.contentScrollView.setVisibility(View.VISIBLE);
        binding.tvMealName.setText(currentMeal.getStrMeal());
        binding.tvArea.setText(currentMeal.getStrArea());
        setupIngredientsAdapter();
        setupInstructionsAdapter();
        setUpMealInstructionsRv();
        setUpIngredientsRv();
        binding.rvInstructions.setNestedScrollingEnabled(false);
        if (currentMeal.getLocalImageBytes() != null && currentMeal.getLocalImageBytes().length > 0) {
            Glide.with(this).load(currentMeal.getLocalImageBytes()).into(binding.ivMealImage);
        } else {
            Glide.with(this).load(currentMeal.getStrMealThumb()).into(binding.ivMealImage);
        }
    }

    private void toggleFavorite() {
        if (userPrefs.isGuest()) {
            showGuestDialog();
            return;
        }
        if (currentMeal == null) return;

        isFavorite = !isFavorite;
        updateFavoriteIcon();

        if (isFavorite) {
            presenter.addToFavorites(currentMeal, requireContext());
        } else {
            presenter.removeFromFavorites(currentMeal);
        }
    }

    private void showGuestDialog() {
        new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                .setTitle("Guest Mode")
                .setMessage("You must login to save favorites or plan meals.")
                .setPositiveButton("Login", (dialog, which) -> {
                    userPrefs.saveGuestMode(false);
                    Intent intent = new Intent(requireActivity(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                })
                .setNegativeButton("Cancel", null)
                .show();
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
            binding.videoContainer.setVisibility(View.VISIBLE);

            getLifecycle().addObserver(binding.youtubePlayerView);

            binding.youtubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                    String videoId = getVideoId(videoUrl);

                    if (videoId != null) {
                        youTubePlayer.cueVideo(videoId, 0);
                    }
                }
            });
        } else {
            binding.videoContainer.setVisibility(View.GONE);
        }
    }

    private String getVideoId(String videoUrl) {
        if (videoUrl != null && videoUrl.contains("v=")) {
            String[] split = videoUrl.split("v=");
            if (split.length > 1) {
                return split[1];
            }
        }
        return null;
    }

    private void showDayPicker() {
        final String[] days = {"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};

        new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Choose a Day")
                .setItems(days, (dialog, which) -> {
                    String selectedDay = days[which];
                    saveMealToPlan(selectedDay);
                })
                .show();
    }
    private void saveMealToPlan(String day) {
        if (currentMeal == null) return;

        PlanMeal plan = new PlanMeal(
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
//        binding.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
//        binding.progressBar.setVisibility(View.GONE);
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
        if (binding != null) {
            binding.youtubePlayerView.release();
        }
        binding = null;
    }
}