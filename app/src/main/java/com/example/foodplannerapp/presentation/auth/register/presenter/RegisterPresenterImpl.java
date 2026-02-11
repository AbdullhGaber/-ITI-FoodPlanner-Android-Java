package com.example.foodplannerapp.presentation.auth.register.presenter;

import com.example.foodplannerapp.data.reposetories.auth.register.repository.RegisterRepository;
import com.example.foodplannerapp.data.utils.NetworkResponseCallback;
import com.example.foodplannerapp.presentation.auth.register.views.RegisterView;
import com.google.firebase.auth.AuthResult;

public class RegisterPresenterImpl implements RegisterPresenter {
    private final RegisterRepository registerRepository;
    private final RegisterView registerView;
    public RegisterPresenterImpl(RegisterRepository registerRepository, RegisterView registerView) {
        this.registerRepository = registerRepository;
        this.registerView = registerView;
    }
    @Override
    public void createAccount(String email, String password) {
        registerView.showProgressbar();
        registerRepository.createAccount(email, password, new NetworkResponseCallback<>() {
            @Override
            public void onSuccess(AuthResult result) {
                registerView.onRegisterSuccess();
                registerView.hideProgressbar();
            }

            @Override
            public void onFail(String message) {
                registerView.onRegisterFailed("Request Error", message);
                registerView.hideProgressbar();
            }

            @Override
            public void onServerError(String message) {
                registerView.onRegisterFailed("Server Error", message);
                registerView.hideProgressbar();
            }
        });
    }
}
