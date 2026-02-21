package com.example.foodplannerapp.data.reposetories.auth.register.repository;

import com.example.foodplannerapp.data.model.user.User;
import com.example.foodplannerapp.data.utils.NetworkResponseCallback;
import com.google.firebase.auth.AuthResult;


public interface RegisterRepository {
    void createAccount(String email, String username, String password, NetworkResponseCallback<AuthResult> callback);
}
