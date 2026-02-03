package com.example.foodplannerapp.data.utils;

public interface NetworkResponseCallback<T> {
    void onSuccess(T result);
    void onFail(String message);
    void onServerError(String message);
}
