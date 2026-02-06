package com.example.foodplannerapp.presentation.activities;

import static androidx.core.splashscreen.SplashScreen.*;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import com.example.foodplannerapp.R;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen splashScreen = installSplashScreen(this);
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        final boolean[] keepOnScreen = {true};
        new Handler(getMainLooper()).postDelayed(() -> keepOnScreen[0] = false, 3000);

        splashScreen.setKeepOnScreenCondition(() -> keepOnScreen[0]);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, IntroActivity.class);
        startActivity(intent);
    }
}