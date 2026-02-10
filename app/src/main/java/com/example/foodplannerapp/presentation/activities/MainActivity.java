package com.example.foodplannerapp.presentation.activities;

import static androidx.core.splashscreen.SplashScreen.*;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.foodplannerapp.R;
import com.example.foodplannerapp.data.datasources.user.UserPreferenceDataSource;
import com.example.foodplannerapp.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    @Inject
    UserPreferenceDataSource userPrefs;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen splashScreen = installSplashScreen(this);
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        final boolean[] keepOnScreen = {true};
        new Handler(getMainLooper()).postDelayed(() -> keepOnScreen[0] = false, 3000);

        splashScreen.setKeepOnScreenCondition(() -> keepOnScreen[0]);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = new Intent(this, IntroActivity.class);
        startActivity(intent);

        checkLoginState();
    }

    private void checkLoginState() {
        if (userPrefs.isUserLoggedIn() && FirebaseAuth.getInstance().getCurrentUser() != null) {
            Intent intent = new Intent(this, FoodActivity.class);
            startActivity(intent);
        }
    }
}