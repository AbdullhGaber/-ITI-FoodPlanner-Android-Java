package com.example.foodplannerapp.presentation.auth.login.presenter;

import com.example.foodplannerapp.data.reposetories.auth.login.repository.LoginRepository;
import com.example.foodplannerapp.data.utils.NetworkResponseCallback;
import com.example.foodplannerapp.presentation.auth.login.views.LoginView;
import com.google.firebase.auth.AuthResult;

import javax.inject.Inject;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class LoginPresenterImpl implements LoginPresenter {
    private final LoginRepository loginRepository;
    private final LoginView loginView;
    private CompositeDisposable disposables = new CompositeDisposable();

    @Inject
    public LoginPresenterImpl(LoginRepository loginRepository, LoginView loginView) {
        this.loginRepository = loginRepository;
        this.loginView = loginView;
    }

    @Override
    public void login(String email, String password) {
        loginRepository.login(email, password, new NetworkResponseCallback<AuthResult>() {
            @Override
            public void onSuccess(AuthResult result) {
                loginView.onLoginSuccess();
            }

            @Override
            public void onFail(String message) {
                loginView.onLoginFailed("Request Error", message);
            }

            @Override
            public void onServerError(String message) {
                loginView.onLoginFailed("Server Error", message);
            }
        });
    }

    @Override
    public void loginWithGoogle(String idToken) {
        loginRepository.loginWithGoogle(idToken, new NetworkResponseCallback<AuthResult>() {
            @Override
            public void onSuccess(AuthResult result) {
                loginView.onLoginSuccess();
            }

            @Override
            public void onFail(String message) {
                loginView.onLoginFailed("Google Sign-In Error", message);
            }

            @Override
            public void onServerError(String message) {
                loginView.onLoginFailed("Server Error", message);
            }
        });
    }

    @Override
    public void onDestroy() {
        disposables.clear();
    }
}
