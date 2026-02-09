package com.example.foodplannerapp.presentation.home.view.categories;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodplannerapp.R;
import com.example.foodplannerapp.data.model.meal_category.Category;
import com.example.foodplannerapp.presentation.home.presenter.areas.AllAreasPresenter;
import com.example.foodplannerapp.presentation.home.presenter.categories.AllCategoriesPresenter;
import com.example.foodplannerapp.presentation.home.view.adapters.CategoryAdapter;
import com.example.foodplannerapp.presentation.utils.GridSpacingItemDecoration;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AllCategoriesFragment extends Fragment implements AllCategoriesView {
    
    @Inject
    AllCategoriesPresenter presenter;
    private CategoryAdapter adapter;
    private RecyclerView areaRv;
    private ShimmerFrameLayout shimmer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_all, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initViews(view);
        setUpRecyclerViewAdapter();
        presenter.observeAllCategories();
    }

    private void setUpRecyclerViewAdapter() {
        int spanCount = 2;

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.grid_spacing);

        areaRv.setLayoutManager(new GridLayoutManager(requireContext(), spanCount));

        areaRv.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacingInPixels, true));

        adapter = new CategoryAdapter(true);

        areaRv.setAdapter(adapter);
    }



    private void initViews(View view){
        TextView title = view.findViewById(R.id.tvTitle);
        title.setText(R.string.all_categories);
        ImageView btnBack = view.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());
        areaRv = view.findViewById(R.id.rvAllItems);
        shimmer = view.findViewById(R.id.allAreas_shimmer);
    }

    @Override
    public void showCategories(List<Category> categories) {
        adapter.submitList(categories);
    }
    @Override
    public void showError(String msg) {
        Snackbar.make(requireView(),msg,Snackbar.ANIMATION_MODE_FADE).show();
    }

    @Override
    public void showShimmer() {
        shimmer.showShimmer(true);
        shimmer.setVisibility(VISIBLE);
    }

    @Override
    public void hideShimmer() {
        shimmer.showShimmer(false);
        shimmer.setVisibility(GONE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}