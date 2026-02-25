package com.example.foodplannerapp.presentation.activities;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.foodplannerapp.R;
import com.example.foodplannerapp.databinding.ActivityFoodBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FoodActivity extends AppCompatActivity {
    private ActivityFoodBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityFoodBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(binding.foodNavHostFragment.getId());

        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();
            NavigationUI.setupWithNavController(binding.bottomNavigation, navController);
            navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
                if (destination.getId() == R.id.homeFragment ||
                        destination.getId() == R.id.favoriteFragment ||
                        destination.getId() == R.id.plannerFragment ||
                        destination.getId() == R.id.profileFragment) {
                    binding.bottomNavigation.setVisibility(View.VISIBLE);
                } else {
                    binding.bottomNavigation.setVisibility(View.GONE);
                }
            });
        }
    }
}