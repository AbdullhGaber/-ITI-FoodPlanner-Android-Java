package com.example.foodplannerapp.data.model.meal_ingeredient;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient {
    private String idIngredient;
    private String strIngredient;
    private String strDescription;
    private String strThumb;
    private String strType;
}