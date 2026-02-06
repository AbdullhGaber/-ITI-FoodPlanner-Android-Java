package com.example.foodplannerapp.data.model.meal_category;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    private String idCategory;
    private String strCategory;
    private String strCategoryThumb;
    private String strCategoryDescription;
}