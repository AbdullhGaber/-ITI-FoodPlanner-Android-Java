package com.example.foodplannerapp.data.reposetories.auth.register.repository;

import com.example.foodplannerapp.data.datasources.user.UserPreferenceDataSource;
import com.example.foodplannerapp.data.model.user.User;
import com.example.foodplannerapp.data.utils.NetworkResponseCallback;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import javax.inject.Inject;

public class RegisterRepositoryImpl implements RegisterRepository {
    private final FirebaseAuth auth;
    private final FirebaseFirestore firestore;
    @Inject
    UserPreferenceDataSource userPreferenceDataSource;
    @Inject
    public RegisterRepositoryImpl() {
        this.auth = FirebaseAuth.getInstance();
        this.firestore = FirebaseFirestore.getInstance();
    }
    @Override
    public void createAccount(String email, String username, String password, NetworkResponseCallback<AuthResult> callback) {
        try{
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener(
                            authResult ->{
                                User user = new User(
                                        authResult.getUser().getUid(),
                                        username,
                                        email
                                );
                                addUserToFireStore(user);
                                userPreferenceDataSource.saveGuest(false);
                                userPreferenceDataSource.setLoginState(true, user.getEmail(), user.getName());
                                callback.onSuccess(authResult);
                            }
                    )
                    .addOnFailureListener(
                            (ex) -> callback.onFail(ex.getMessage())
                    );
        } catch (Exception e) {
            callback.onServerError(e.getMessage());
        }
    }
    private void addUserToFireStore(User user) {
        DocumentReference df = firestore.collection("user_collection").document(user.getUid());
        df.set(user);
    }
}
