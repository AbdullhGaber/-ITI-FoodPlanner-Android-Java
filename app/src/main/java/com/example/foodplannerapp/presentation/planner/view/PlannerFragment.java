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
import com.example.foodplannerapp.data.db.meals.entities.PlanMeal;
import com.example.foodplannerapp.databinding.FragmentPlannerBinding;
import com.example.foodplannerapp.presentation.planner.presenter.PlannerPresenter;
import com.example.foodplannerapp.presentation.planner.view.adapters.PlannerAdapter;
import com.google.android.material.chip.Chip;

import java.util.List;
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
        setupChips();
        
        // Initial Load (Saturday)
        presenter.getMealsForDay("Saturday");
    }

    private void setupRecyclerView() {
        adapter = new PlannerAdapter(this);
        binding.rvPlanner.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvPlanner.setAdapter(adapter);
    }

    private void setupChips() {
        binding.chipGroupDays.setOnCheckedChangeListener((group, checkedId) -> {
            Chip chip = group.findViewById(checkedId);
            if (chip != null) {
                presenter.getMealsForDay(chip.getText().toString());
            }
        });
    }

    @Override
    public void showPlannedMeals(List<PlanMeal> plans) {
        adapter.submitList(plans);
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
    public void onPlanClick(PlanMeal plan) {
        PlannerFragmentDirections.ActionPlannerFragmentToMealDetailsFragment action =
                PlannerFragmentDirections.actionPlannerFragmentToMealDetailsFragment(plan.getMealId());
        Navigation.findNavController(binding.getRoot()).navigate(action);
    }

    @Override
    public void onDeletePlanClick(PlanMeal plan) {
        presenter.deletePlan(plan);
    }
    
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDestroy();
        binding = null;
    }
}