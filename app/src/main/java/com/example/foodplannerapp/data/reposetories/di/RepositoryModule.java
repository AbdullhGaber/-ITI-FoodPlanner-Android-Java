package com.example.foodplannerapp.data.reposetories.di;

import com.example.foodplannerapp.data.reposetories.auth.login.repository.LoginRepository;
import com.example.foodplannerapp.data.reposetories.auth.login.repository.LoginRepositoryImpl;
import com.example.foodplannerapp.data.reposetories.auth.register.repository.RegisterRepository;
import com.example.foodplannerapp.data.reposetories.auth.register.repository.RegisterRepositoryImpl;
import com.example.foodplannerapp.data.reposetories.backup.BackupRepository;
import com.example.foodplannerapp.data.reposetories.backup.BackupRepositoryImpl;
import com.example.foodplannerapp.data.reposetories.meals.MealsRepository;
import com.example.foodplannerapp.data.reposetories.meals.MealsRepositoryImpl;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;


@Module
@InstallIn(SingletonComponent.class)
public interface RepositoryModule {

    //Auth
    @Binds
    LoginRepository getLoginRepository(LoginRepositoryImpl loginRepository);
    @Binds
    RegisterRepository getRegisterRepository(RegisterRepositoryImpl registerRepository);

    //Meals
    @Binds
    MealsRepository getMealsRepository(MealsRepositoryImpl mealsRepository);
    @Binds
    BackupRepository getBackUpRepository(BackupRepositoryImpl mealsRepository);
}
