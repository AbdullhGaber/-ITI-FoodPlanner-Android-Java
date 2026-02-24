package com.example.foodplannerapp.presentation.home.view.home;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.foodplannerapp.R;
import com.example.foodplannerapp.data.model.meal.Meal;
import com.example.foodplannerapp.data.model.meal_area.Area;
import com.example.foodplannerapp.data.model.meal_category.Category;
import com.example.foodplannerapp.databinding.FragmentHomeBinding;
import com.example.foodplannerapp.presentation.home.presenter.home.HomePresenter;
import com.example.foodplannerapp.presentation.home.view.adapters.AreaAdapter;
import com.example.foodplannerapp.presentation.home.view.adapters.CategoryAdapter;
import com.example.foodplannerapp.presentation.home.view.adapters.MealCarouselAdapter;
import com.example.foodplannerapp.presentation.utils.Constants;
import com.example.foodplannerapp.presentation.utils.Dialogs;
import com.example.foodplannerapp.presentation.utils.Dialogs.ErrorStrategy;
import com.example.foodplannerapp.presentation.utils.Dialogs.SuccessStrategy;
import com.example.foodplannerapp.presentation.utils.ShimmerUtil;
import java.util.List;
import javax.inject.Inject;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFragment extends Fragment implements HomeView{
    @Inject
    HomePresenter homePresenter;
    FragmentHomeBinding binding;
    AreaAdapter areaAdapter;
    CategoryAdapter categoryAdapter;
    Meal currentMeal;
    private MealCarouselAdapter carouselAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkAndShowAuthSuccessDialog();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        binding.mealDayContainer.setOnClickListener(
                (v) ->{
                    if(currentMeal != null){
                        homePresenter.onRandomMealClick();
                    }
                }
        );
        setUpRvAdapters();
        observeData();
    }

    private void checkAndShowAuthSuccessDialog() {
        if (requireActivity().getIntent() != null) {

            boolean showLoginSuccess = requireActivity().getIntent().getBooleanExtra(
                    Constants.SP_LOGIN_KEY, false);

            boolean showRegisterSuccess = requireActivity().getIntent().getBooleanExtra(
                    Constants.SP_REGISTER_KEY, false);

            if (showLoginSuccess) {
                Dialogs.show(
                        requireContext(),
                        new SuccessStrategy(),
                        "Logged In Successfully",
                        "Explore and enjoy meals to add to your plans",
                        "Let's Get Started",
                        "",
                        new Dialogs.OnDialogActionListener() {
                            @Override
                            public void onPositiveClick(Dialog dialog) {
                                dialog.dismiss();
                            }

                            @Override
                            public void onNegativeClick(Dialog dialog) {
                                dialog.dismiss();
                            }
                        }
                );

                requireActivity().getIntent().removeExtra("SHOW_REGISTER_SUCCESS");
            }else if(showRegisterSuccess){
                Dialogs.show(
                        requireContext(),
                        new SuccessStrategy(),
                        "Account Registered Successfully",
                        "Explore and enjoy meals to add to your plans",
                        "Let's Get Started",
                        "",
                        new Dialogs.OnDialogActionListener() {
                            @Override
                            public void onPositiveClick(Dialog dialog) {
                                dialog.dismiss();
                            }

                            @Override
                            public void onNegativeClick(Dialog dialog) {
                                dialog.dismiss();
                            }
                        }
                );
            }
        }
    }

    @Override
    public void navigateToMealDetailsFragment() {
        HomeFragmentDirections.ActionHomeFragmentToMealDetailsFragment action =
                HomeFragmentDirections.actionHomeFragmentToMealDetailsFragment(currentMeal.getIdMeal());
        Navigation.findNavController(requireView()).navigate(action);
    }



    private void observeData() {
        if(homePresenter.isGuest()) {
            hideAreaShimmer();
        } else {
            homePresenter.observeAllAreas();
        }

        homePresenter.observeAllCategories();
        homePresenter.observeRandomMeal();

        homePresenter.observeCarouselMeals();
    }

    private void setUpRvAdapters() {
        areaAdapter = new AreaAdapter();
        categoryAdapter = new CategoryAdapter();
        if(!homePresenter.isGuest())
            binding.recyclerAreas.setAdapter(areaAdapter);

        binding.recyclerCategories.setAdapter(categoryAdapter);

        carouselAdapter = new MealCarouselAdapter();
        binding.vpMealsCarousel.setAdapter(carouselAdapter);
        carouselAdapter.setListener(
                (meal) -> {
                    homePresenter.onCarouselMealClick(meal);
                }
        );

        binding.vpMealsCarousel.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                if (state == ViewPager2.SCROLL_STATE_DRAGGING) {
                    homePresenter.stopAutoScroll();
                } else if (state == ViewPager2.SCROLL_STATE_IDLE) {
                    homePresenter.startAutoScroll();
                }
            }
        });
    }

    @Override
    public void navigateToMealDetailsWithId(String mealId) {
        HomeFragmentDirections.ActionHomeFragmentToMealDetailsFragment action =
                HomeFragmentDirections.actionHomeFragmentToMealDetailsFragment(mealId);
        Navigation.findNavController(requireView()).navigate(action);
    }

    @Override
    public void showCarouselMeals(List<Meal> meals) {
        carouselAdapter.submitList(meals);
    }

    @Override
    public void showCarouselShimmer() {
        binding.carouselShimmer.showShimmer(true);
        binding.carouselShimmer.setVisibility(View.VISIBLE);
        binding.vpMealsCarousel.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideCarouselShimmer() {
        binding.carouselShimmer.showShimmer(false);
        binding.carouselShimmer.setVisibility(View.GONE);
        binding.vpMealsCarousel.setVisibility(View.VISIBLE);
    }

    @Override
    public void moveToNextCarouselItem() {
        if (carouselAdapter.getItemCount() > 0) {
            int nextItem = binding.vpMealsCarousel.getCurrentItem() + 1;
            if (nextItem >= carouselAdapter.getItemCount()) {
                nextItem = 0;
            }
            binding.vpMealsCarousel.setCurrentItem(nextItem, true);
        }
    }

    private void initViews() {
        if(homePresenter.isGuest()){
            binding.tvIngredientsTitle.setVisibility(GONE);
            binding.tvSeeAllCountries.setVisibility(GONE);
            binding.recyclerAreas.setVisibility(GONE);
        }

        binding.tvHomeTitle.setText(homePresenter.getUsername());

        binding.tvSeeAllCountries.setOnClickListener(
                v -> Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_allAreasFragment)
        );

        binding.tvSeeAllCategories.setOnClickListener(
                v -> Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_allCategoriesFragment)
        );

        binding.ivSearch.setOnClickListener(
                (v) -> Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_searchFragment)
        );
    }
    @Override
    public void showRandomMeal(List<Meal> meals) {
        currentMeal = meals.get(0);
        ShimmerUtil.addShimmerToImage(requireContext(), currentMeal.getStrMealThumb(), binding.imgMealDay);
        binding.tvMealDayName.setText(currentMeal.getStrMeal());
        binding.tvMealDayDesc.setText(String.format("%s • %s", currentMeal.getStrArea(), currentMeal.getStrCategory()));
    }
    @Override
    public void showAreas(List<Area> areas) {
        areaAdapter.submitList(areas);
    }

    @Override
    public void showProgressbar() {
        binding.progressBarHome.setVisibility(VISIBLE);
        binding.loadingOverlayHome.setVisibility(VISIBLE);
    }

    @Override
    public void hideProgressbar() {
        binding.progressBarHome.setVisibility(GONE);
        binding.loadingOverlayHome.setVisibility(GONE);
    }

    @Override
    public void showCategories(List<Category> categories) {
        categoryAdapter.submitList(categories);
    }

    @Override
    public void showError(String msg) {
        Dialogs.show(
                requireContext(),
                new ErrorStrategy(),
                "An Error Occurred",
                msg,
                "Ok",
                "",
                new Dialogs.OnDialogActionListener() {
                    @Override
                    public void onPositiveClick(Dialog dialog) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onNegativeClick(Dialog dialog) {
                        dialog.dismiss();
                    }
                }
        );
    }

    @Override
    public void showAreaShimmer() {
        binding.areaShimmer.showShimmer(true);
        binding.areaShimmer.setVisibility(VISIBLE);
    }

    @Override
    public void hideAreaShimmer() {
        binding.areaShimmer.showShimmer(false);
        binding.areaShimmer.setVisibility(GONE);
    }

    @Override
    public void showCategoryShimmer() {
        binding.categoryShimmer.showShimmer(true);
        binding.categoryShimmer.setVisibility(VISIBLE);
    }

    @Override
    public void hideRandomMealShimmer() {
        binding.mealShimmer.showShimmer(false);
        binding.mealShimmer.setVisibility(GONE);
        binding.cardMealOfDay.setVisibility(VISIBLE);
    }

    @Override
    public void showRandomMealShimmer() {
        binding.mealShimmer.showShimmer(true);
        binding.mealShimmer.setVisibility(VISIBLE);
    }

    @Override
    public void hideCategoryShimmer() {
        binding.categoryShimmer.showShimmer(false);
        binding.categoryShimmer.setVisibility(GONE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        homePresenter.onDestroy();
        homePresenter = null;
    }
}