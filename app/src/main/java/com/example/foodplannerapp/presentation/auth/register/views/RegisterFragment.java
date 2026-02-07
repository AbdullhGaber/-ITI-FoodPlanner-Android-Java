package com.example.foodplannerapp.presentation.auth.register.views;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.foodplannerapp.R;
import com.example.foodplannerapp.data.reposetories.auth.register.repository.RegisterRepositoryImpl;
import com.example.foodplannerapp.presentation.activities.FoodActivity;
import com.example.foodplannerapp.presentation.auth.register.presenter.RegisterPresenter;
import com.example.foodplannerapp.presentation.auth.register.presenter.RegisterPresenterImpl;
import com.example.foodplannerapp.presentation.utils.Dialogs;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterFragment extends Fragment implements RegisterView {
    TextView redirectLoginTv;
    EditText fullNameEt;
    EditText emailEt;
    EditText passwordEt;
    EditText confirmPasswordEt;
    Button registerBtn;
    RegisterPresenter presenter;
    TextInputLayout fullNameLayout;
    TextInputLayout emailLayout;
    TextInputLayout passwordLayout;
    TextInputLayout confirmPasswordLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new RegisterPresenterImpl(
                new RegisterRepositoryImpl(),
                this
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        setOnRegisterButtonClick();
        setOnRegisterRedirectClick();
    }

    private void setOnRegisterButtonClick() {
        registerBtn.setOnClickListener(
                (v) -> {
                    String name = fullNameEt.getText().toString().trim();
                    String email = emailEt.getText().toString().trim();
                    String password = passwordEt.getText().toString().trim();
                    String confirmPassword = confirmPasswordEt.getText().toString().trim();

                    if (validateInputs(name, email, password, confirmPassword)) {
                        presenter.createAccount(email, password);
                    }
                }
        );
    }

    private void initViews(View view) {
        redirectLoginTv = view.findViewById(R.id.redirect_login_clickable);
        fullNameEt = view.findViewById(R.id.register_full_name_et);
        emailEt = view.findViewById(R.id.register_email_et);
        passwordEt = view.findViewById(R.id.register_password_et);
        confirmPasswordEt = view.findViewById(R.id.register_confirm_password_et);
        registerBtn = view.findViewById(R.id.register_button);
        fullNameLayout = view.findViewById(R.id.register_full_name_layout);
        emailLayout = view.findViewById(R.id.register_email_layout);
        passwordLayout = view.findViewById(R.id.register_password_layout);
        confirmPasswordLayout = view.findViewById(R.id.register_confirm_password_layout);
    }

    private boolean validateInputs(String name, String email, String password, String confirmPassword) {
        boolean isValid = true;

        fullNameLayout.setError(null);
        emailLayout.setError(null);
        passwordLayout.setError(null);
        confirmPasswordLayout.setError(null);

        if (name.isEmpty()) {
            fullNameLayout.setError("Full name is required");
            isValid = false;
        } else if (name.length() < 3) {
            fullNameLayout.setError("Full name must be at least 3 characters");
            isValid = false;
        }

        if (email.isEmpty()) {
            emailLayout.setError("Email is required");
            isValid = false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailLayout.setError("Please enter a valid email address");
            isValid = false;
        }

        if (password.isEmpty()) {
            passwordLayout.setError("Password is required");
            isValid = false;
        } else if (password.length() < 6) {
            passwordLayout.setError("Password must be at least 6 characters");
            isValid = false;
        }

        if (confirmPassword.isEmpty()) {
            confirmPasswordLayout.setError("Please confirm your password");
            isValid = false;
        } else if (!password.equals(confirmPassword)) {
            confirmPasswordLayout.setError("Passwords do not match");
            isValid = false;
        }

        return isValid;
    }

    private void setOnRegisterRedirectClick() {
        redirectLoginTv.setOnClickListener(
                (v) -> Navigation.findNavController(v).navigate(R.id.action_registerFragment_to_loginFragment)
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

}