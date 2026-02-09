package com.example.foodplannerapp.presentation.auth.login.views;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodplannerapp.data.reposetories.auth.login.repository.LoginRepositoryImpl;
import com.example.foodplannerapp.databinding.FragmentLoginBinding;
import com.example.foodplannerapp.presentation.activities.FoodActivity;
import com.example.foodplannerapp.presentation.auth.login.presenter.LoginPresenter;
import com.example.foodplannerapp.presentation.auth.login.presenter.LoginPresenterImpl;
import com.example.foodplannerapp.presentation.utils.Dialogs;

public class LoginFragment extends Fragment implements LoginView {
    LoginPresenter presenter;
    FragmentLoginBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new LoginPresenterImpl(
                new LoginRepositoryImpl(),
                this
        );
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        setOnLoginButtonClickListener();
        setOnRegisterRedirectClick();
        setOnContinueAsAGuestClick();
    }

    private void setOnRegisterRedirectClick() {
        binding.redirectRegisterClickable.setOnClickListener(
                (v) -> Navigation.findNavController(v).navigate(com.example.foodplannerapp.R.id.action_loginFragment_to_registerFragment)
        );
    }

    private void setOnContinueAsAGuestClick() {
        binding.guestModeTv.setOnClickListener((v) -> onLoginSuccess());
    }

    private void setOnLoginButtonClickListener() {
        binding.loginButton.setOnClickListener(
                (v) -> {
                    String email = binding.loginEmailEt.getText().toString().trim();
                    String password = binding.loginPasswordEt.getText().toString().trim();

                    if (validateInputs(email, password)) {
                        presenter.login(email, password);
                    }
                }
        );
    }

    private void initViews() {
        // Views are now accessed through the binding object
    }

    private boolean validateInputs(String email, String password) {
        boolean isValid = true;

        binding.loginEmailLayout.setError(null);
        binding.loginPasswordLayout.setError(null);


        if (email.isEmpty()) {
            binding.loginEmailLayout.setError("Email is required");
            isValid = false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.loginEmailLayout.setError("Please enter a valid email address");
            isValid = false;
        }

        if (password.isEmpty()) {
            binding.loginPasswordLayout.setError("Password is required");
            isValid = false;
        } else if (password.length() < 6) {
            binding.loginPasswordLayout.setError("Password must be at least 6 characters");
            isValid = false;
        }

        return isValid;
    }

    @Override
    public void onLoginSuccess() {
        Intent intent = new Intent(getActivity(), FoodActivity.class);
        startActivity(intent);
        requireActivity().finish();
    }
    @Override
    public void onLoginFailed(String title, String message) {
        Dialogs.showAlertDialog(requireContext(), title, message);
    }

}