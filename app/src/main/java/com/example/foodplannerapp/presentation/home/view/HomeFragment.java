package com.example.foodplannerapp.presentation.home.view;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodplannerapp.R;
import com.example.foodplannerapp.data.model.meal_area.Area;
import com.example.foodplannerapp.presentation.home.presenter.HomePresenter;
import com.example.foodplannerapp.presentation.home.view.adapters.AreaAdapter;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFragment extends Fragment implements HomeView{
    @Inject
    HomePresenter homePresenter;
    RecyclerView areasRv;
    AreaAdapter areaAdapter;
    ShimmerFrameLayout areaShimmer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        setUpRvAdapters();
        homePresenter.observeAllArea();
    }

    private void setUpRvAdapters() {
        areaAdapter = new AreaAdapter();
        areasRv.setAdapter(areaAdapter);
    }

    private void initViews() {
        areasRv = requireView().findViewById(R.id.recycler_areas);
        areaShimmer = requireView().findViewById(R.id.areaShimmer);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        homePresenter.onDestroy();
    }

    @Override
    public void showAreas(List<Area> areas) {
        areaAdapter.submitList(areas);
    }

    @Override
    public void showError(String msg) {
        Snackbar.make(requireView(),msg,Snackbar.ANIMATION_MODE_FADE).show();
    }

    @Override
    public void showShimmer() {
        areaShimmer.showShimmer(true);
        areaShimmer.setVisibility(VISIBLE);
    }

    @Override
    public void hideShimmer() {
        areaShimmer.showShimmer(false);
        areaShimmer.setVisibility(GONE);
    }
}