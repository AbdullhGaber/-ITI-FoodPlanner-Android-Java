package com.example.foodplannerapp.data.db.meals.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(tableName = "plan_table")
@Data
@NoArgsConstructor
public class PlanMeal {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String mealId;
    private String dayOfWeek;
    
    private String strMeal;
    private String strMealThumb;
    private String strArea;
    private String strCategory;
    

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] imageBytes; 

    public PlanMeal(String mealId, String dayOfWeek, String strMeal, String strMealThumb, String strArea, String strCategory, byte[] imageBytes) {
        this.mealId = mealId;
        this.dayOfWeek = dayOfWeek;
        this.strMeal = strMeal;
        this.strMealThumb = strMealThumb;
        this.strArea = strArea;
        this.strCategory = strCategory;
        this.imageBytes = imageBytes;
    }
}