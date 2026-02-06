package com.example.foodplannerapp.data.reposetories.auth.login.repository;

import com.example.foodplannerapp.data.utils.NetworkResponseCallback;
import com.google.firebase.auth.AuthResult;

public interface LoginRepository {
    void login(String email, String password, NetworkResponseCallback<AuthResult> callback);
}
