package com.example.foodplannerapp.presentation.meals.view.meals_search;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.foodplannerapp.R;
import com.example.foodplannerapp.data.model.meal.Meal;
import com.example.foodplannerapp.data.reposetories.meals.MealsRepository;
import com.example.foodplannerapp.databinding.FragmentSearchBinding;
import com.example.foodplannerapp.presentation.meals.presenter.meals_search.SearchPresenter;
import com.example.foodplannerapp.presentation.meals.view.adapters.SearchAdapter;
import com.google.android.material.snackbar.Snackbar;


import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SearchFragment extends Fragment implements SearchView, SearchAdapter.OnMealClickListener {

    @Inject
    SearchPresenter presenter;
    private FragmentSearchBinding binding;
    private SearchAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView();
        setupSearchInput();
        setupChipGroup();

        binding.btnBack.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());

        // Check for incoming arguments from Categories or Areas screens
        if (getArguments() != null) {
            SearchFragmentArgs args = SearchFragmentArgs.fromBundle(getArguments());
            String query = args.getSearchQuery();
            String type = args.getSearchType();

            if (query != null && type != null) {
                binding.chipGroupSearchType.clearCheck();

                if (type.equals("Category")) {
                    binding.chipGroupSearchType.check(R.id.chip_category);
                    presenter.setSearchType(MealsRepository.SearchType.CATEGORY);
                } else if (type.equals("Area")) {
                    binding.chipGroupSearchType.check(R.id.chip_area);
                    presenter.setSearchType(MealsRepository.SearchType.AREA);
                }

                binding.etSearch.setText(query);
            }
        }
    }

    private void setupRecyclerView() {
        adapter = new SearchAdapter(this);
        binding.rvSearchResults.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvSearchResults.setAdapter(adapter);
    }

    private void setupSearchInput() {
        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.search(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void setupChipGroup() {
        binding.chipGroupSearchType.setOnCheckedStateChangeListener((group, checkedIds) -> {

            boolean isName = checkedIds.contains(R.id.chip_name);
            boolean isCategory = checkedIds.contains(R.id.chip_category);
            boolean isArea = checkedIds.contains(R.id.chip_area);
            boolean isIngredient = checkedIds.contains(R.id.chip_ingredient);

            presenter.updateActiveFilters(isName, isCategory, isArea, isIngredient);

            String currentText = binding.etSearch.getText().toString();
            if (!currentText.isEmpty()) {
                presenter.search(currentText);
            }
        });
    }

    @Override
    public void showError(String message) {
        Snackbar.make(requireView(),message,Snackbar.ANIMATION_MODE_FADE).show();
    }


    @Override
    public void onMealClick(Meal meal) {
        SearchFragmentDirections.ActionSearchFragmentToMealDetailsFragment action =
                SearchFragmentDirections.actionSearchFragmentToMealDetailsFragment(meal.getIdMeal());
        Navigation.findNavController(binding.getRoot()).navigate(action);
    }

    @Override
    public void showSearchResults(List<Meal> meals) {
        binding.rvSearchResults.setVisibility(View.VISIBLE);
        binding.layoutSearchInitial.setVisibility(View.GONE);
        binding.layoutNotFound.setVisibility(View.GONE);
        adapter.submitList(meals);
    }

    @Override
    public void showInitialState() {
        binding.rvSearchResults.setVisibility(View.GONE);
        binding.layoutNotFound.setVisibility(View.GONE);
        binding.layoutSearchInitial.setVisibility(View.VISIBLE);
        adapter.submitList(new ArrayList<>());
    }

    @Override
    public void showNotFoundState() {
        binding.rvSearchResults.setVisibility(View.GONE);
        binding.layoutSearchInitial.setVisibility(View.GONE);
        binding.layoutNotFound.setVisibility(View.VISIBLE);
        adapter.submitList(new ArrayList<>());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDestroy();
        binding = null;
    }
}