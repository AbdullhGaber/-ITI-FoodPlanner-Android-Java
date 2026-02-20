package com.example.foodplannerapp.presentation.utils;

import android.content.Context;
import android.app.Dialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.ViewGroup;
import androidx.core.content.ContextCompat;
import com.example.foodplannerapp.R;
import com.example.foodplannerapp.databinding.DialogCustomAlertBinding;
public class Dialogs {
    public interface OnDialogActionListener {
        void onPositiveClick(Dialog dialog);
        void onNegativeClick(Dialog dialog);
    }

    public static void show(Context context, DialogStrategy strategy, String title, String message, String positiveText, String negativeText, OnDialogActionListener listener) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        DialogCustomAlertBinding binding = DialogCustomAlertBinding.inflate(LayoutInflater.from(context));
        dialog.setContentView(binding.getRoot());

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        binding.tvDialogTitle.setText(title);
        binding.tvDialogMessage.setText(message);
        binding.btnDialogAction.setText(positiveText);
        binding.btnDialogCancel.setText(negativeText);

        strategy.applyTheme(context,binding);

        setButtonsClickListeners(listener, binding, dialog);

        dialog.show();
    }

    private static void setButtonsClickListeners(OnDialogActionListener listener, DialogCustomAlertBinding binding, Dialog dialog) {
        binding.btnDialogAction.setOnClickListener(v -> {
            if (listener != null) listener.onPositiveClick(dialog);
            else dialog.dismiss();
        });

        binding.btnDialogCancel.setOnClickListener(v -> {
            if (listener != null) listener.onNegativeClick(dialog);
            else dialog.dismiss();
        });
    }

    public interface DialogStrategy {
        void applyTheme(Context context, DialogCustomAlertBinding binding);
    }

    public static class SuccessStrategy implements DialogStrategy {
        @Override
        public void applyTheme(Context context, DialogCustomAlertBinding binding) {
            binding.lottieDialogIcon.setAnimation(R.raw.success);
            binding.tvDialogTitle.setTextColor(ContextCompat.getColor(context, R.color.green_header));
            binding.btnDialogAction.setBackgroundTintList(
                    ColorStateList.valueOf(ContextCompat.getColor(context, R.color.green_header))
            );
        }
    }

    public static class ErrorStrategy implements DialogStrategy {
        @Override
        public void applyTheme(Context context, DialogCustomAlertBinding binding) {
            binding.lottieDialogIcon.setAnimation(R.raw.error);
            binding.tvDialogTitle.setTextColor(ContextCompat.getColor(context, R.color.red_error));
            binding.btnDialogAction.setBackgroundTintList(
                    ColorStateList.valueOf(ContextCompat.getColor(context, R.color.red_error))
            );
        }
    }

    public static class WarningStrategy implements DialogStrategy {
        @Override
        public void applyTheme(Context context, DialogCustomAlertBinding binding) {
            binding.lottieDialogIcon.setAnimation(R.raw.warning);
            binding.tvDialogTitle.setTextColor(ContextCompat.getColor(context, R.color.orange));
            binding.btnDialogAction.setBackgroundTintList(
                    ColorStateList.valueOf(ContextCompat.getColor(context, R.color.orange))
            );
        }
    }
}
