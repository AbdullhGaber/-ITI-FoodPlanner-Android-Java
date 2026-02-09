package com.example.foodplannerapp.data.network.di;

import static com.example.foodplannerapp.data.utils.Constants.BASE_URL;

import com.example.foodplannerapp.data.network.MealService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class NetworkModule{
    @Provides
    @Singleton
    Retrofit getRetrofitClient(){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    MealService getMealService(Retrofit retrofit){
        return retrofit.create(MealService.class);
    }
}
