package com.example.foodplannerapp.data.reposetories.auth.login.repository;

import com.example.foodplannerapp.data.datasources.user.UserPreferenceDataSource;
import com.example.foodplannerapp.data.model.user.User;
import com.example.foodplannerapp.data.utils.NetworkResponseCallback;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import javax.inject.Inject;

public class LoginRepositoryImpl implements LoginRepository {
    private final FirebaseAuth auth;
    private final FirebaseFirestore firestore;
    @Inject
    UserPreferenceDataSource userPreferenceDataSource;
    @Inject
    public LoginRepositoryImpl() {
        this.auth = FirebaseAuth.getInstance();
        this.firestore = FirebaseFirestore.getInstance();
    }
    @Override
    public void login(String email, String password, NetworkResponseCallback<AuthResult> callback) {
        try{
            auth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener((authResult) -> {
                        loadUserInSharedPreferences(authResult.getUser().getUid());
                        callback.onSuccess(authResult);
                    })
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
                    .addOnSuccessListener((authResult) -> {
                        loadUserInSharedPreferences(authResult.getUser().getUid());
                        callback.onSuccess(authResult);
                    })
                    .addOnFailureListener(
                            (ex) -> callback.onServerError(ex.getMessage())
                    );
        } catch (Exception e) {
            callback.onFail(e.getLocalizedMessage());
        }
    }

    private void loadUserInSharedPreferences(String uid){
        var task = firestore.collection("user_collection").document(uid).get();

        task.addOnSuccessListener(
                (ds) -> {
                    User user = new User(
                            ds.get("uid").toString(),
                            ds.get("name").toString(),
                            ds.get("email").toString()
                    );
                    userPreferenceDataSource.saveGuest(false);
                    userPreferenceDataSource.setLoginState(true,user.getEmail(), user.getName());
                }
        );
    }
}