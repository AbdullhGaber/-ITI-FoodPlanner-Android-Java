package com.example.foodplannerapp.data.utils;
import android.content.Context;
import com.example.foodplannerapp.data.model.meal.Meal;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import java.io.File;

public class MealMapper {
    public static Meal toModel(com.example.foodplannerapp.data.db.meals.entities.Meal entity) {
        if (entity == null) return null;

        Meal meal = new Meal();
        meal.setIdMeal(entity.getIdMeal());
        meal.setStrMeal(entity.getStrMeal());
        meal.setStrCategory(entity.getStrCategory());
        meal.setStrArea(entity.getStrArea());
        meal.setStrInstructions(entity.getStrInstructions());
        meal.setStrMealThumb(entity.getStrMealThumb());
        meal.setStrYoutube(entity.getStrYoutube());
        meal.setStrTags(entity.getStrTags());
        meal.setStrIngredient1(entity.getStrIngredient1());
        meal.setStrIngredient2(entity.getStrIngredient2());
        meal.setStrIngredient3(entity.getStrIngredient3());
        meal.setStrIngredient4(entity.getStrIngredient4());
        meal.setStrIngredient5(entity.getStrIngredient5());
        meal.setStrIngredient6(entity.getStrIngredient6());
        meal.setStrIngredient7(entity.getStrIngredient7());
        meal.setStrIngredient8(entity.getStrIngredient8());
        meal.setStrIngredient9(entity.getStrIngredient9());
        meal.setStrIngredient10(entity.getStrIngredient10());
        meal.setStrIngredient11(entity.getStrIngredient11());
        meal.setStrIngredient12(entity.getStrIngredient12());
        meal.setStrIngredient13(entity.getStrIngredient13());
        meal.setStrIngredient14(entity.getStrIngredient14());
        meal.setStrIngredient15(entity.getStrIngredient15());
        meal.setStrIngredient16(entity.getStrIngredient16());
        meal.setStrIngredient17(entity.getStrIngredient17());
        meal.setStrIngredient18(entity.getStrIngredient18());
        meal.setStrIngredient19(entity.getStrIngredient19());
        meal.setStrIngredient20(entity.getStrIngredient20());
        meal.setStrMeasure1(entity.getStrMeasure1());
        meal.setStrMeasure2(entity.getStrMeasure2());
        meal.setStrMeasure3(entity.getStrMeasure3());
        meal.setLocalImageBytes(entity.getLocalImageBytes());

        return meal;
    }

    public static com.example.foodplannerapp.data.db.meals.entities.Meal toEntity(Meal meal, Context context) {
        if (meal == null) return null;

        com.example.foodplannerapp.data.db.meals.entities.Meal entity = new com.example.foodplannerapp.data.db.meals.entities.Meal();
        entity.setIdMeal(meal.getIdMeal());
        entity.setStrMeal(meal.getStrMeal());
        entity.setStrCategory(meal.getStrCategory());
        entity.setStrArea(meal.getStrArea());
        entity.setStrInstructions(meal.getStrInstructions());
        entity.setStrMealThumb(meal.getStrMealThumb());
        entity.setStrYoutube(meal.getStrYoutube());
        entity.setStrTags(meal.getStrTags());
        entity.setStrIngredient1(meal.getStrIngredient1());
        entity.setStrIngredient2(meal.getStrIngredient2());
        entity.setStrIngredient3(meal.getStrIngredient3());
        entity.setStrIngredient4(meal.getStrIngredient4());
        entity.setStrIngredient5(meal.getStrIngredient5());
        entity.setStrIngredient6(meal.getStrIngredient6());
        entity.setStrIngredient7(meal.getStrIngredient7());
        entity.setStrIngredient8(meal.getStrIngredient8());
        entity.setStrIngredient9(meal.getStrIngredient9());
        entity.setStrIngredient10(meal.getStrIngredient10());
        entity.setStrIngredient11(meal.getStrIngredient11());
        entity.setStrIngredient12(meal.getStrIngredient12());
        entity.setStrIngredient13(meal.getStrIngredient13());
        entity.setStrIngredient14(meal.getStrIngredient14());
        entity.setStrIngredient15(meal.getStrIngredient15());
        entity.setStrIngredient16(meal.getStrIngredient16());
        entity.setStrIngredient17(meal.getStrIngredient17());
        entity.setStrIngredient18(meal.getStrIngredient18());
        entity.setStrIngredient19(meal.getStrIngredient19());
        entity.setStrIngredient20(meal.getStrIngredient20());
        entity.setStrMeasure1(meal.getStrMeasure1());
        entity.setStrMeasure2(meal.getStrMeasure2());
        entity.setStrMeasure3(meal.getStrMeasure3());
        
        // Download and store image bytes locally
        if (meal.getStrMealThumb() != null && !meal.getStrMealThumb().isEmpty()) {
            try {
                File imageFile = Glide.with(context)
                        .asFile()
                        .load(meal.getStrMealThumb())
                        .submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .get();
                
                // Convert file to byte array
                byte[] imageBytes = new byte[(int) imageFile.length()];
                try (java.io.FileInputStream fis = new java.io.FileInputStream(imageFile)) {
                    fis.read(imageBytes);
                }
                entity.setLocalImageBytes(imageBytes);
            } catch (Exception e) {
                // If image download fails, just skip storing image bytes
                e.printStackTrace();
            }
        }
        
        entity.setLocalImageBytes(meal.getLocalImageBytes());
        return entity;
    }
}