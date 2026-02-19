package com.example.foodplannerapp.data.reposetories.backup;

import io.reactivex.rxjava3.core.Completable;

public interface BackupRepository {
     Completable backupData();
     Completable restoreData();
}