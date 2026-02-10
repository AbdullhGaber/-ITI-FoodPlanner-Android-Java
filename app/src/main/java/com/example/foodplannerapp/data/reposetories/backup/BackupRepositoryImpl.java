package com.example.foodplannerapp.data.reposetories.backup;

import com.example.foodplannerapp.data.db.meals.dao.MealDao;
import com.example.foodplannerapp.data.db.meals.dao.PlanDao;
import com.example.foodplannerapp.data.db.meals.entities.Meal;
import com.example.foodplannerapp.data.db.meals.entities.PlanMeal;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

import java.util.List;
import javax.inject.Inject;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class BackupRepositoryImpl implements BackupRepository {
    private final MealDao mealDao;
    private final PlanDao planDao;
    private final FirebaseFirestore db;
    private final FirebaseAuth auth;

    @Inject
    public BackupRepositoryImpl(MealDao mealDao, PlanDao planDao) {
        this.mealDao = mealDao;
        this.planDao = planDao;
        this.db = FirebaseFirestore.getInstance();
        this.auth = FirebaseAuth.getInstance();
    }

    @Override
    public Completable backupData() {
        return Single.zip(
                mealDao.getAllMeals().firstOrError(),
                planDao.getAllPlans().firstOrError(),
                (meals, plans) -> {
                    String userId = auth.getCurrentUser().getUid();
                    WriteBatch batch = db.batch();

                    for (Meal meal : meals) {
                        meal.setLocalImageBytes(null);
                        batch.set(db.collection("users").document(userId).collection("favorites").document(meal.getIdMeal()), meal);
                    }

                    for (PlanMeal plan : plans) {
                        plan.setImageBytes(null);
                        String planId = plan.getDayOfWeek() + "_" + plan.getMealId();
                        batch.set(db.collection("users").document(userId).collection("plans").document(planId), plan);
                    }
                    
                    return batch;
                }
        ).flatMapCompletable(batch -> Completable.create(emitter -> {
            batch.commit()
                    .addOnSuccessListener(aVoid -> emitter.onComplete())
                    .addOnFailureListener(emitter::onError);
        }));
    }
    @Override
    public Completable restoreData() {
        return Completable.create(emitter -> {
            String userId = auth.getCurrentUser().getUid();

            db.collection("users").document(userId).collection("favorites").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Meal> meals = queryDocumentSnapshots.toObjects(Meal.class);
                    for(Meal meal : meals) {
                        mealDao.insertMeal(meal).subscribeOn(Schedulers.io()).subscribe();
                    }
                })
                .addOnFailureListener(emitter::onError);

            db.collection("users").document(userId).collection("plans").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<PlanMeal> plans = queryDocumentSnapshots.toObjects(PlanMeal.class);

                    for(PlanMeal plan : plans) {
                        planDao.insertPlan(plan).subscribeOn(Schedulers.io()).subscribe();
                    }
                    emitter.onComplete();
                }).addOnFailureListener(emitter::onError);
        });
    }
}