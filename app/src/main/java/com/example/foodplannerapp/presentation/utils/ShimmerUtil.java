package com.example.foodplannerapp.presentation.utils;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.viewbinding.ViewBinding;

import com.bumptech.glide.Glide;
import com.example.foodplannerapp.R;
import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerDrawable;
import com.facebook.shimmer.ShimmerFrameLayout;

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

    public static void  showShimmer(ShimmerFrameLayout shimmerViewContainer){
        Shimmer shimmer = ShimmerUtil.getShimmer();
        ShimmerDrawable shimmerDrawable = new ShimmerDrawable();
        shimmerDrawable.setShimmer(shimmer);
        shimmerViewContainer.setShimmer(shimmer);
        shimmerViewContainer.startShimmer();
        shimmerViewContainer.setVisibility(View.VISIBLE);
    }
    public static void  hideShimmer(ShimmerFrameLayout shimmerViewContainer){
        shimmerViewContainer.stopShimmer();
        shimmerViewContainer.setVisibility(View.GONE);
    }
}
