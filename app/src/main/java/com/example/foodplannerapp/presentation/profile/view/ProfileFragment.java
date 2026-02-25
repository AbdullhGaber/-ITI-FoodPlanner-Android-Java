package com.example.foodplannerapp.presentation.profile.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.foodplannerapp.R;
import com.example.foodplannerapp.databinding.FragmentProfileBinding;
import com.example.foodplannerapp.presentation.activities.MainActivity;
import com.example.foodplannerapp.presentation.profile.presenter.ProfilePresenter;
import javax.inject.Inject;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ProfileFragment extends Fragment implements ProfileView {
    @Inject
    ProfilePresenter presenter;
    private FragmentProfileBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.observeMealCounts();
        presenter.observeUserData();
        setOnClickListeners();
    }
    @Override
    public void showSuccess(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showFavoritesCount(int count) {
        binding.tvFavoritesCount.setText(String.valueOf(count));
    }

    @Override
    public void showPlansCount(int count) {
        binding.tvPlannedCount.setText(String.valueOf(count));
    }

    @Override
    public void displayUserData(String username, String email) {
        binding.tvUsername.setText(username);
        binding.tvEmail.setText(email);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToLogin() {
        Intent intent = new Intent(requireActivity(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        requireActivity().finish();
    }

    private void setOnClickListeners() {
        binding.cardPlannedMeals.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_profileFragment_to_plannerFragment);
        });

        binding.cardFavoriteMeals.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_profileFragment_to_favoriteFragment);
        });

        binding.btnLogout.setOnClickListener(v -> {
            presenter.logout();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDestroy();
        binding = null;
    }
}