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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.foodplannerapp.R;
import com.example.foodplannerapp.data.reposetories.auth.login.repository.LoginRepositoryImpl;
import com.example.foodplannerapp.presentation.activities.FoodActivity;
import com.example.foodplannerapp.presentation.auth.login.presenter.LoginPresenter;
import com.example.foodplannerapp.presentation.auth.login.presenter.LoginPresenterImpl;
import com.example.foodplannerapp.presentation.utils.Dialogs;
import com.google.android.material.textfield.TextInputLayout;

public class LoginFragment extends Fragment implements LoginView {
    LoginPresenter presenter;
    EditText emailEt;
    EditText passwordEt;
    Button loginButtonEt;
    TextView redirectRegisterTv;
    TextView guestModeTv;
    TextInputLayout emailLayout;
    TextInputLayout passwordLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new LoginPresenterImpl(
                new LoginRepositoryImpl(),
                this
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        setOnLoginButtonClickListener();
        setOnRegisterRedirectClick();
        setOnContinueAsAGuestClick();
    }

    private void setOnRegisterRedirectClick() {
        redirectRegisterTv.setOnClickListener(
                (v) -> Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_registerFragment)
        );
    }

    private void setOnContinueAsAGuestClick() {
        guestModeTv.setOnClickListener((v) -> onLoginSuccess());
    }

    private void setOnLoginButtonClickListener() {
        loginButtonEt.setOnClickListener(
                (v) -> {
                    String email = emailEt.getText().toString().trim();
                    String password = passwordEt.getText().toString().trim();

                    if (validateInputs(email, password)) {
                        presenter.login(email, password);
                    }
                }
        );
    }

    private void initViews(View view) {
        emailEt = view.findViewById(R.id.login_email_et);
        passwordEt = view.findViewById(R.id.login_password_et);
        loginButtonEt = view.findViewById(R.id.login_button);
        redirectRegisterTv = view.findViewById(R.id.redirect_register_clickable);
        guestModeTv = view.findViewById(R.id.guest_mode_tv);
        emailLayout = view.findViewById(R.id.login_email_layout);
        passwordLayout = view.findViewById(R.id.login_password_layout);
    }

    private boolean validateInputs(String email, String password) {
        boolean isValid = true;

        emailLayout.setError(null);
        passwordLayout.setError(null);


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