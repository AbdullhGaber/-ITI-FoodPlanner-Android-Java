package com.example.foodplannerapp.data.auth.register.repository;

import com.example.foodplannerapp.data.utils.NetworkResponseCallback;
import com.google.firebase.auth.AuthResult;

public interface RegisterRepository {
    void createAccount(String email, String password, NetworkResponseCallback<AuthResult> callback);
}
