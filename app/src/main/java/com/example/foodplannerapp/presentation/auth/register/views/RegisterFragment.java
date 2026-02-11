package com.example.foodplannerapp.presentation.auth.register.views;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodplannerapp.data.reposetories.auth.register.repository.RegisterRepositoryImpl;
import com.example.foodplannerapp.databinding.FragmentRegisterBinding;
import com.example.foodplannerapp.presentation.activities.FoodActivity;
import com.example.foodplannerapp.presentation.auth.register.presenter.RegisterPresenter;
import com.example.foodplannerapp.presentation.auth.register.presenter.RegisterPresenterImpl;
import com.example.foodplannerapp.presentation.utils.Dialogs;

public class RegisterFragment extends Fragment implements RegisterView {
    RegisterPresenter presenter;
    FragmentRegisterBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new RegisterPresenterImpl(
                new RegisterRepositoryImpl(),
                this
        );
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setOnRegisterButtonClick();
        setOnRegisterRedirectClick();
    }

    private void setOnRegisterButtonClick() {
        binding.registerButton.setOnClickListener(
                (v) -> {
                    String name = binding.registerFullNameEt.getText().toString().trim();
                    String email = binding.registerEmailEt.getText().toString().trim();
                    String password = binding.registerPasswordEt.getText().toString().trim();
                    String confirmPassword = binding.registerConfirmPasswordEt.getText().toString().trim();

                    if (validateInputs(name, email, password, confirmPassword)) {
                        presenter.createAccount(email, password);
                    }
                }
        );
    }

    private boolean validateInputs(String name, String email, String password, String confirmPassword) {
        boolean isValid = true;

        binding.registerFullNameLayout.setError(null);
        binding.registerEmailLayout.setError(null);
        binding.registerPasswordLayout.setError(null);
        binding.registerConfirmPasswordLayout.setError(null);

        if (name.isEmpty()) {
            binding.registerFullNameLayout.setError("Full name is required");
            isValid = false;
        } else if (name.length() < 3) {
            binding.registerFullNameLayout.setError("Full name must be at least 3 characters");
            isValid = false;
        }

        if (email.isEmpty()) {
            binding.registerEmailLayout.setError("Email is required");
            isValid = false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.registerEmailLayout.setError("Please enter a valid email address");
            isValid = false;
        }

        if (password.isEmpty()) {
            binding.registerPasswordLayout.setError("Password is required");
            isValid = false;
        } else if (password.length() < 6) {
            binding.registerPasswordLayout.setError("Password must be at least 6 characters");
            isValid = false;
        }

        if (confirmPassword.isEmpty()) {
            binding.registerConfirmPasswordLayout.setError("Please confirm your password");
            isValid = false;
        } else if (!password.equals(confirmPassword)) {
            binding.registerConfirmPasswordLayout.setError("Passwords do not match");
            isValid = false;
        }

        return isValid;
    }

    private void setOnRegisterRedirectClick() {
        binding.redirectLoginClickable.setOnClickListener(
                (v) -> Navigation.findNavController(v).navigate(com.example.foodplannerapp.R.id.action_registerFragment_to_loginFragment)
        );
    }

    @Override
    public void onRegisterSuccess() {
        Intent intent = new Intent(getActivity(), FoodActivity.class);
        startActivity(intent);
        requireActivity().finish();
    }

    @Override
    public void onRegisterFailed(String title, String message) {
        Dialogs.showAlertDialog(requireContext(), title, message);
    }

    @Override
    public void showProgressbar() {
        binding.progressBar3.setVisibility(VISIBLE);
        binding.loadingOverlay.setVisibility(VISIBLE);
    }

    @Override
    public void hideProgressbar() {
        binding.progressBar3.setVisibility(GONE);
        binding.loadingOverlay.setVisibility(GONE);
    }

}