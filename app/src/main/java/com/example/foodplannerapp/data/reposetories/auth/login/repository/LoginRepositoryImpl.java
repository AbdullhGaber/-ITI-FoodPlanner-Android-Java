package com.example.foodplannerapp.data.reposetories.auth.login.repository;

import com.example.foodplannerapp.data.utils.NetworkResponseCallback;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import javax.inject.Inject;

public class LoginRepositoryImpl implements LoginRepository {
    private final FirebaseAuth auth;
    @Inject
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

    @Override
    public void loginWithGoogle(String idToken, NetworkResponseCallback<AuthResult> callback) {
        try {
            com.google.firebase.auth.AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
            auth.signInWithCredential(credential)
                    .addOnSuccessListener(callback::onSuccess)
                    .addOnFailureListener(
                            (ex) -> callback.onServerError(ex.getMessage())
                    );
        } catch (Exception e) {
            callback.onFail(e.getLocalizedMessage());
        }
    }
}
// AE:58:E4:23:CC:5F:7B:15:73:4D:65:A2:C4:0C:50:2C:0D:37:8B:86
//6066479135-o38acc5o26kpfu2plr566d7kej3a9aa8.apps.googleusercontent.com