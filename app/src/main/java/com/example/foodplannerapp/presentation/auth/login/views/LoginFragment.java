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
import com.example.foodplannerapp.data.auth.login.repository.LoginRepositoryImpl;
import com.example.foodplannerapp.presentation.activites.FoodActivity;
import com.example.foodplannerapp.presentation.auth.login.presenter.LoginPresenter;
import com.example.foodplannerapp.presentation.auth.login.presenter.LoginPresenterImpl;
import com.example.foodplannerapp.presentation.utils.Dialogs;

public class LoginFragment extends Fragment implements LoginView {
    LoginPresenter presenter;
    EditText emailEt;
    EditText passwordEt;
    Button loginButtonEt;
    TextView redirectRegisterTv;
    TextView guestModeTv;

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
                    String email = emailEt.getText().toString();
                    String password = passwordEt.getText().toString();

                    if(!email.isBlank() && !password.isBlank()){
                        presenter.login(email,password);
                    }else{
                        Dialogs.showAlertDialog(requireContext(),"Inputs Missing", "Email and Password are required");
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