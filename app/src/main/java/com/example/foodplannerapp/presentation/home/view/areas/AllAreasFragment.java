package com.example.foodplannerapp.presentation.home.view.areas;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.foodplannerapp.R;
import com.example.foodplannerapp.data.model.meal_area.Area;
import com.example.foodplannerapp.databinding.FragmentViewAllBinding;
import com.example.foodplannerapp.presentation.home.presenter.areas.AllAreasPresenter;
import com.example.foodplannerapp.presentation.home.view.adapters.AreaAdapter;
import com.example.foodplannerapp.presentation.utils.GridSpacingItemDecoration;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AllAreasFragment extends Fragment implements AllAreasView {
    
    @Inject
    AllAreasPresenter presenter;
    private AreaAdapter adapter;
    private FragmentViewAllBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentViewAllBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initViews();
        setUpRecyclerViewAdapter();
        presenter.observeAllAreas();
    }

    private void setUpRecyclerViewAdapter() {
        int spanCount = 2;

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.grid_spacing);

        binding.rvAllItems.setLayoutManager(new GridLayoutManager(requireContext(), spanCount));

        binding.rvAllItems.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacingInPixels, true));

        adapter = new AreaAdapter(true);
        binding.rvAllItems.setAdapter(adapter);
    }

    @Override
    public void showAreas(List<Area> areas) {
        adapter.submitList(areas);
    }

    private void initViews(){
        binding.tvTitle.setText(R.string.all_countries);
        binding.btnBack.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());
    }

    @Override
    public void showError(String msg) {
        Snackbar.make(requireView(),msg,Snackbar.ANIMATION_MODE_FADE).show();
    }

    @Override
    public void showShimmer() {
        binding.allAreasShimmer.showShimmer(true);
        binding.allAreasShimmer.setVisibility(VISIBLE);
    }

    @Override
    public void hideShimmer() {
        binding.allAreasShimmer.showShimmer(false);
        binding.allAreasShimmer.setVisibility(GONE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}