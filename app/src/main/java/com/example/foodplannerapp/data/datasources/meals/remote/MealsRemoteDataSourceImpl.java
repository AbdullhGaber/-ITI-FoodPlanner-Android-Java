package com.example.foodplannerapp.data.datasources.meals.remote;

import static com.example.foodplannerapp.data.utils.Constants.FAVORITES_COLLECTION;
import static com.example.foodplannerapp.data.utils.Constants.PLANS_COLLECTION;
import static com.example.foodplannerapp.data.utils.Constants.USERS_COLLECTION;

import com.example.foodplannerapp.data.db.meals.entities.MealEntity;
import com.example.foodplannerapp.data.model.meal.MealResponse;
import com.example.foodplannerapp.data.model.meal_area.AreaListResponse;
import com.example.foodplannerapp.data.model.meal_category.CategoryResponse;
import com.example.foodplannerapp.data.network.MealService;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;

public class MealsRemoteDataSourceImpl implements MealsRemoteDataSource{
    private final MealService mealService;
    private final FirebaseFirestore firestore;

    @Inject
    public MealsRemoteDataSourceImpl(MealService mealService) {
        this.mealService = mealService;
        this.firestore = FirebaseFirestore.getInstance();
    }

    @Override
    public Single<MealResponse> getRandomMeal() {
        return mealService.getRandomMeal();
    }

    @Override
    public Single<CategoryResponse> getAllMealsCategories() {
        return mealService.getAllMealsCategories();
    }

    @Override
    public Maybe<MealResponse> getMealById(String id) {
        return mealService.getMealById(id);
    }

    @Override
    public Single<AreaListResponse> getAllAreas() {
        return mealService.getAllArea();
    }
    @Override
    public Single<MealResponse> searchByName(String query) {
        return mealService.searchByName(query);
    }

    @Override
    public Single<MealResponse> searchByIngredient(String query) {
        return mealService.filterByIngredient(query);
    }

    @Override
    public Single<MealResponse> searchByArea(String query) {
        return mealService.filterByArea(query);
    }

    @Override
    public Single<MealResponse> searchByCategory(String query) {
        return mealService.filterByCategory(query);
    }
    @Override
    public Completable backupFavorite(String userId, MealEntity meal) {
        return Completable.create(emitter -> {
            firestore.collection(USERS_COLLECTION).document(userId)
                    .collection(FAVORITES_COLLECTION).document(meal.getIdMeal())
                    .set(meal)
                    .addOnSuccessListener(aVoid -> {
                        System.out.println("Added favorite with uid : " + userId);
                        if (!emitter.isDisposed()) emitter.onComplete();
                    })
                    .addOnFailureListener(e -> {
                        System.out.println("Failed Added favorite with uid : " + userId);
                        System.out.println("Error " + e);
                        if (!emitter.isDisposed()) emitter.onError(e);
                    });
        });
    }

    @Override
    public Completable removeBackupFavorite(String userId, String mealId) {
        return Completable.create(emitter -> {
            firestore.collection(USERS_COLLECTION).document(userId)
                    .collection(FAVORITES_COLLECTION).document(mealId)
                    .delete()
                    .addOnSuccessListener(aVoid -> {
                        if (!emitter.isDisposed()) emitter.onComplete();
                    })
                    .addOnFailureListener(e -> {
                        if (!emitter.isDisposed()) emitter.onError(e);
                    });
        });
    }

    @Override
    public Completable backupPlan(String userId, MealEntity meal) {
        return Completable.create(emitter -> {
            firestore.collection(USERS_COLLECTION).document(userId)
                    .collection(PLANS_COLLECTION).document(meal.getIdMeal())
                    .set(meal)
                    .addOnSuccessListener(aVoid -> {
                        if (!emitter.isDisposed()) emitter.onComplete();
                    })
                    .addOnFailureListener(e -> {
                        if (!emitter.isDisposed()) emitter.onError(e);
                    });
        });
    }

    @Override
    public Completable removeBackupPlan(String userId, String mealId) {
        return Completable.create(emitter -> {
            firestore.collection(USERS_COLLECTION).document(userId)
                    .collection(PLANS_COLLECTION).document(mealId)
                    .delete()
                    .addOnSuccessListener(aVoid -> {
                        if (!emitter.isDisposed()) emitter.onComplete();
                    })
                    .addOnFailureListener(e -> {
                        if (!emitter.isDisposed()) emitter.onError(e);
                    });
        });
    }

    @Override
    public Single<List<MealEntity>> getBackedUpFavorites(String userId) {
        return Single.create(emitter -> {
            firestore.collection(USERS_COLLECTION).document(userId)
                    .collection(FAVORITES_COLLECTION)
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        List<MealEntity> meals = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            MealEntity meal = doc.toObject(MealEntity.class);
                            meals.add(meal);
                        }
                        if (!emitter.isDisposed()) emitter.onSuccess(meals);
                    })
                    .addOnFailureListener(e -> {
                        if (!emitter.isDisposed()) emitter.onError(e);
                    });
        });
    }

    @Override
    public Single<List<MealEntity>> getBackedUpPlans(String userId) {
        return Single.create(emitter -> {
            firestore.collection(USERS_COLLECTION).document(userId)
                    .collection(PLANS_COLLECTION)
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        List<MealEntity> meals = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            MealEntity meal = doc.toObject(MealEntity.class);
                            meals.add(meal);
                        }
                        if (!emitter.isDisposed()) emitter.onSuccess(meals);
                    })
                    .addOnFailureListener(e -> {
                        if (!emitter.isDisposed()) emitter.onError(e);
                    });
        });
    }
}
