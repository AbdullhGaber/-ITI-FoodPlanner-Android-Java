package com.example.foodplannerapp.data.reposetories.auth.login.repository;

import static com.example.foodplannerapp.data.utils.Constants.USERS_COLLECTION;
import com.example.foodplannerapp.data.datasources.meals.local.MealsLocalDataSource;
import com.example.foodplannerapp.data.datasources.meals.remote.MealsRemoteDataSource;
import com.example.foodplannerapp.data.datasources.user.UserPreferenceDataSource;
import com.example.foodplannerapp.data.model.user.User;
import com.example.foodplannerapp.data.utils.NetworkResponseCallback;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import javax.inject.Inject;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginRepositoryImpl implements LoginRepository {
    private final FirebaseAuth auth;
    private final FirebaseFirestore firestore;
    private final MealsRemoteDataSource mealsRemoteDataSource;
    private final MealsLocalDataSource mealsLocalDataSource;
    @Inject
    UserPreferenceDataSource userPreferenceDataSource;

    @Inject
    public LoginRepositoryImpl(MealsRemoteDataSource mealsRemoteDataSource, MealsLocalDataSource mealsLocalDataSource) {
        this.mealsRemoteDataSource = mealsRemoteDataSource;
        this.mealsLocalDataSource = mealsLocalDataSource;
        this.auth = FirebaseAuth.getInstance();
        this.firestore = FirebaseFirestore.getInstance();
    }

    @Override
    public void login(String email, String password, NetworkResponseCallback<AuthResult> callback) {
        try {
            auth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener((authResult) -> {
                        loadUserAndSyncData(authResult.getUser().getUid(), authResult, callback);
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
            AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
            auth.signInWithCredential(credential)
                    .addOnSuccessListener((authResult) -> {
                        loadUserAndSyncData(authResult.getUser().getUid(), authResult, callback);
                    })
                    .addOnFailureListener(
                            (ex) -> callback.onServerError(ex.getMessage())
                    );
        } catch (Exception e) {
            callback.onFail(e.getLocalizedMessage());
        }
    }

    private void loadUserAndSyncData(String uid, AuthResult authResult, NetworkResponseCallback<AuthResult> callback) {
        firestore.collection(USERS_COLLECTION).document(uid).get()
                .addOnSuccessListener((ds) -> {
                    if (ds.exists() && ds.get("uid") != null) {
                        User user = new User(
                                ds.get("uid").toString(),
                                ds.get("name").toString(),
                                ds.get("email").toString()
                        );
                        userPreferenceDataSource.saveGuest(false);
                        userPreferenceDataSource.setLoginState(true, user.getEmail(), user.getName(), user.getUid());
                    }

                    syncRemoteDataToLocal(uid)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    () -> callback.onSuccess(authResult),
                                    (error) -> callback.onSuccess(authResult)
                            );
                }).addOnFailureListener((ex) -> callback.onServerError(ex.getMessage()));
    }

    private Completable syncRemoteDataToLocal(String userId) {
        Completable syncFavorites = mealsRemoteDataSource.getBackedUpFavorites(userId)
                .observeOn(Schedulers.io())
                .flatMapCompletable(favorites -> {
                    if (favorites.isEmpty()) return Completable.complete();
                    return mealsLocalDataSource.insertMeals(favorites);
                });

        Completable syncPlans = mealsRemoteDataSource.getBackedUpPlans(userId)
                .observeOn(Schedulers.io())
                .flatMapCompletable(plans -> {
                    if (plans.isEmpty()) return Completable.complete();
                    return mealsLocalDataSource.insertMeals(plans);
                });

        return syncFavorites.andThen(syncPlans).onErrorComplete();
    }
}