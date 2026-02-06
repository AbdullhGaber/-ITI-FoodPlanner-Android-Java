package com.example.foodplannerapp.data.reposetories.auth.login.repository;

import com.example.foodplannerapp.data.utils.NetworkResponseCallback;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginRepositoryImpl implements LoginRepository {
    private final FirebaseAuth auth;
    public LoginRepositoryImpl() {
        this.auth = FirebaseAuth.getInstance();
    }
    @Override
    public void login(String email, String password, NetworkResponseCallback<AuthResult> callback) {
        try{
            auth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener(callback::onSuccess)
                    .addOnFailureListener(
                            (ex) -> callback.onServerError(ex.getMessage())
                    );
        } catch (Exception e) {
            callback.onFail(e.getLocalizedMessage());
        }
    }
}
