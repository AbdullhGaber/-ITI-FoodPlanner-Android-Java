package com.example.foodplannerapp.presentation.planner.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.foodplannerapp.R;
import com.example.foodplannerapp.data.db.meals.entities.MealEntity;
import com.example.foodplannerapp.databinding.FragmentPlannerBinding;
import com.example.foodplannerapp.presentation.planner.view.adapters.CalendarAdapter;
import com.example.foodplannerapp.presentation.model.CalendarDateModel;
import com.example.foodplannerapp.presentation.planner.presenter.PlannerPresenter;
import com.example.foodplannerapp.presentation.planner.view.adapters.PlannerAdapter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.inject.Inject;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PlannerFragment extends Fragment implements PlannerView, PlannerAdapter.OnPlanClickListener {
    @Inject
    PlannerPresenter presenter;
    private FragmentPlannerBinding binding;
    private PlannerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPlannerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView();
        setupCalendarRecyclerView();
    }

    private void setupRecyclerView() {
        adapter = new PlannerAdapter(this);
        binding.rvPlanner.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvPlanner.setAdapter(adapter);
    }

    private void setupCalendarRecyclerView() {
        CalendarAdapter calendarAdapter = new CalendarAdapter(dateModel -> {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String formattedDate = sdf.format(dateModel.getFullDate());

            presenter.getMealsForDay(formattedDate);
        });

        binding.rvCalendar.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.rvCalendar.setAdapter(calendarAdapter);

        List<CalendarDateModel> upcomingDays = generateNextSevenDays();
        calendarAdapter.submitList(upcomingDays);

        if (!upcomingDays.isEmpty()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String todayFormatted = sdf.format(upcomingDays.get(0).getFullDate());
            presenter.getMealsForDay(todayFormatted);
        }
    }

    private List<CalendarDateModel> generateNextSevenDays() {
        List<CalendarDateModel> dateList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat dayOfWeekFormat = new SimpleDateFormat("EEE", Locale.getDefault());
        SimpleDateFormat dayNumberFormat = new SimpleDateFormat("dd", Locale.getDefault());

        for (int i = 0; i < 7; i++) {
            Date date = calendar.getTime();
            String dayOfWeek = dayOfWeekFormat.format(date);
            String dayNumber = dayNumberFormat.format(date);

            dateList.add(new CalendarDateModel(date, dayOfWeek, dayNumber));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        return dateList;
    }

    @Override
    public void showPlannedMeals(List<MealEntity> plans) {
        if (plans == null || plans.isEmpty()) {
            binding.rvPlanner.setVisibility(View.GONE);
            binding.layoutEmpty.emptyStateContainer.setVisibility(View.VISIBLE);

            binding.layoutEmpty.tvEmptyTitle.setText(R.string.no_planned_meals_yet);
            binding.layoutEmpty.tvEmptySubtitle.setText(R.string.tap_on_calendar_icon_on_a_meal_to_see_it_here);

        } else {
            binding.layoutEmpty.emptyStateContainer.setVisibility(View.GONE);
            binding.rvPlanner.setVisibility(View.VISIBLE);

            adapter.submitList(plans);
        }
    }

    @Override
    public void showError(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSuccess(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPlanClick(MealEntity plan) {
        PlannerFragmentDirections.ActionPlannerFragmentToMealDetailsFragment action =
                PlannerFragmentDirections.actionPlannerFragmentToMealDetailsFragment(plan.getIdMeal());
        Navigation.findNavController(binding.getRoot()).navigate(action);
    }

    @Override
    public void onDeletePlanClick(MealEntity plan) {
        presenter.deletePlan(plan);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDestroy();
        binding = null;
    }
}