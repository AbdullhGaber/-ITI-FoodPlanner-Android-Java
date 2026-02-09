package com.example.foodplannerapp.presentation.meals.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.bumptech.glide.Glide;
import com.example.foodplannerapp.R;
import com.example.foodplannerapp.data.model.meal.Meal;
import com.example.foodplannerapp.databinding.FragmentMealDetailsBinding;
import com.example.foodplannerapp.presentation.meals.view.adapters.IngredientsAdapter;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MealDetailsFragment extends Fragment {

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
            currentMeal = MealDetailsFragmentArgs.fromBundle(getArguments()).getMeal();
        }

        if (currentMeal != null) {
            setupUI();
            setupIngredients();
            setupVideo();
            setupFavoriteToggle();
        }
    }

    private void setupUI() {
        binding.tvMealName.setText(currentMeal.getStrMeal());
        binding.tvArea.setText(currentMeal.getStrArea());
        binding.tvInstructions.setText(currentMeal.getStrInstructions());

        Glide.with(this)
                .load(currentMeal.getStrMealThumb())
                .into(binding.ivMealImage);

        binding.btnBack.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());
    }

    private void setupIngredients() {
        ingredientsAdapter = new IngredientsAdapter();
        binding.rvIngredients.setAdapter(ingredientsAdapter);

        ingredientsAdapter.submitList(currentMeal.getIngredientsList());
    }

    private void setupFavoriteToggle() {
        updateFavoriteIcon();

        binding.btnFavorite.setOnClickListener(v -> {
            isFavorite = !isFavorite;
            updateFavoriteIcon();

            // TODO: Room DB Insert/Delete will go here later
            if (isFavorite) {
                // insertMeal(currentMeal);
            } else {
                // deleteMeal(currentMeal);
            }
        });
    }

    private void updateFavoriteIcon() {
        if (isFavorite) {
            binding.btnFavorite.setImageResource(R.drawable.ic_heart_filled);
            binding.btnFavorite.setColorFilter(null); // Clear any white tint
        } else {
            binding.btnFavorite.setImageResource(R.drawable.ic_heart_unfilled); // The outline
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
        } else {
            binding.videoContainer.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}