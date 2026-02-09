package com.example.foodplannerapp.presentation.utils;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

public class Dialogs {
    public static void showAlertDialog(Context context, String title, String message){
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }
}
