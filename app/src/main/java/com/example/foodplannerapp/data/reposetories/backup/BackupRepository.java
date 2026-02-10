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

public interface BackupRepository {
     Completable backupData();
     Completable restoreData();
}