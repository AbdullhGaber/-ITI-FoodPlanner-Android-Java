package com.example.foodplannerapp.presentation.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.foodplannerapp.R;
import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerDrawable;

public class ShimmerUtil {
    private static Shimmer getShimmer() {
        return new Shimmer.AlphaHighlightBuilder()
                .setDuration(1800)
                .setBaseAlpha(0.7f)
                .setHighlightAlpha(0.6f)
                .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
                .setAutoStart(true)
                .build();
    }

    public static void addShimmerToImage(Context context, String imgStr, ImageView imgView) {
        Shimmer shimmer = getShimmer();
        ShimmerDrawable shimmerDrawable = new ShimmerDrawable();
        shimmerDrawable.setShimmer(shimmer);

        Glide.with(context)
                .load(imgStr)
                .placeholder(shimmerDrawable)
                .error(R.drawable.ic_broken_image)
                .into(imgView);
    }
}
