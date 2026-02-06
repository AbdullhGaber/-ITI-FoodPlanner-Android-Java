package com.example.foodplannerapp.presentation.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodplannerapp.R;

public class IntroActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        ImageView logoIcon = findViewById(R.id.imgLogoIcon);
        TextView tvAppName = findViewById(R.id.tvAppName);
        TextView tvSlogan = findViewById(R.id.tvSlogan);

        float distanceY = 100f;
        logoIcon.setTranslationY(distanceY);
        tvAppName.setTranslationY(distanceY);
        tvSlogan.setTranslationY(distanceY);

        ObjectAnimator iconAlpha = ObjectAnimator.ofFloat(logoIcon, View.ALPHA, 0f, 1f);
        ObjectAnimator iconTranslate = ObjectAnimator.ofFloat(logoIcon, View.TRANSLATION_Y, distanceY, 0f);


        ObjectAnimator iconRotate = ObjectAnimator.ofFloat(logoIcon, View.ROTATION_Y, 0f, 360f);

        ObjectAnimator textAlpha = ObjectAnimator.ofFloat(tvAppName, View.ALPHA, 0f, 1f);
        textAlpha.setStartDelay(200);
        ObjectAnimator textTranslate = ObjectAnimator.ofFloat(tvAppName, View.TRANSLATION_Y, distanceY, 0f);
        textTranslate.setStartDelay(200);

        ObjectAnimator sloganAlpha = ObjectAnimator.ofFloat(tvSlogan, View.ALPHA, 0f, 1f);
        sloganAlpha.setStartDelay(400);
        ObjectAnimator sloganTranslate = ObjectAnimator.ofFloat(tvSlogan, View.TRANSLATION_Y, distanceY, 0f);
        sloganTranslate.setStartDelay(400);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                iconAlpha, iconTranslate, iconRotate,
                textAlpha, textTranslate,
                sloganAlpha, sloganTranslate
        );
        animatorSet.setDuration(4000);
        animatorSet.setInterpolator(new DecelerateInterpolator());

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                logoIcon.postDelayed(() -> navigateToMain(), 1000);
            }
        });

        animatorSet.start();
    }

    private void navigateToMain() {
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}