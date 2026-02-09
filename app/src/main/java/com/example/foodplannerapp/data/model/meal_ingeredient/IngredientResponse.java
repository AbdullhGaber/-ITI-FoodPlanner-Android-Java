package com.example.foodplannerapp.data.model.meal_ingeredient;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngredientResponse {
    private List<Ingredient> meals;
}