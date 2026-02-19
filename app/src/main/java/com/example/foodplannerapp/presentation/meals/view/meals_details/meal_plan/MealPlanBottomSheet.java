package com.example.foodplannerapp.presentation.meals.view.meals_details.meal_plan;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.foodplannerapp.R;
import com.example.foodplannerapp.databinding.BottomSheetMealPlanBinding;
import com.example.foodplannerapp.presentation.meals.view.listeners.OnPlanSaveListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import lombok.Setter;

public class MealPlanBottomSheet extends BottomSheetDialogFragment {
    private BottomSheetMealPlanBinding binding;
    private Calendar calendar;
    @Setter
    private OnPlanSaveListener onPlanSaveListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = BottomSheetMealPlanBinding.inflate(inflater, container, false);

        calendar = Calendar.getInstance();
        updateDateText();

        binding.btnChangeDate.setOnClickListener(v -> showDatePicker());

        binding.tvCancel.setOnClickListener(v -> dismiss());

        binding.btnAddToPlan.setOnClickListener(v -> {
            int selectedId = binding.rgMealType.getCheckedRadioButtonId();
            if (selectedId == -1) {
                Toast.makeText(getContext(), "Please select a meal type", Toast.LENGTH_SHORT).show();
            }

            String mealType = "";
            if (selectedId == R.id.rbBreakfast) mealType = "Breakfast";
            else if (selectedId == R.id.rbLunch) mealType = "Lunch";
            else if (selectedId == R.id.rbDinner) mealType = "Dinner";

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String selectedDate = sdf.format(calendar.getTime());

            if (onPlanSaveListener != null) {
                onPlanSaveListener.onSavePlan(selectedDate, mealType);
            }

            dismiss();
        });

        return binding.getRoot();
    }

    private void showDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                (view, year, month, dayOfMonth) -> {
                    calendar.set(year, month, dayOfMonth);
                    updateDateText();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    private void updateDateText() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMMM dd, yyyy", Locale.getDefault());
        binding.tvSelectedDate.setText(sdf.format(calendar.getTime()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}