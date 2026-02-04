package com.example.foodplannerapp.data.auth.register.repository;

import com.example.foodplannerapp.data.utils.NetworkResponseCallback;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterRepositoryImpl implements RegisterRepository {
    private final FirebaseAuth auth;
    public RegisterRepositoryImpl() {
        this.auth = FirebaseAuth.getInstance();
    }
    @Override
    public void createAccount(String email, String password, NetworkResponseCallback<AuthResult> callback) {
        try{
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener(callback::onSuccess)
                    .addOnFailureListener(
                            (ex) -> callback.onFail(ex.getMessage())
                    );
        } catch (Exception e) {
            callback.onServerError(e.getMessage());
        }
    }
}
