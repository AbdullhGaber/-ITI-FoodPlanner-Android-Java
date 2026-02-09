package com.example.foodplannerapp.data.model.meal;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import com.example.foodplannerapp.data.model.meal_ingeredient.Ingredient;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Meal implements Parcelable {
    private String dateModified;
    private String idMeal;
    private String strArea;
    private String strCategory;
    private Object strCreativeCommonsConfirmed;
    private Object strImageSource;
    private String strIngredient1;
    private String strIngredient10;
    private String strIngredient11;
    private String strIngredient12;
    private String strIngredient13;
    private String strIngredient14;
    private String strIngredient15;
    private String strIngredient16;
    private String strIngredient17;
    private String strIngredient18;
    private String strIngredient19;
    private String strIngredient2;
    private String strIngredient20;
    private String strIngredient3;
    private String strIngredient4;
    private String strIngredient5;
    private String strIngredient6;
    private String strIngredient7;
    private String strIngredient8;
    private String strIngredient9;
    private String strInstructions;
    private String strMeal;
    private Object strMealAlternate;
    private String strMealThumb;
    private String strMeasure1;
    private String strMeasure10;
    private String strMeasure11;
    private String strMeasure12;
    private String strMeasure13;
    private String strMeasure14;
    private String strMeasure15;
    private String strMeasure16;
    private String strMeasure17;
    private String strMeasure18;
    private String strMeasure19;
    private String strMeasure2;
    private String strMeasure20;
    private String strMeasure3;
    private String strMeasure4;
    private String strMeasure5;
    private String strMeasure6;
    private String strMeasure7;
    private String strMeasure8;
    private String strMeasure9;
    private String strSource;
    private String strTags;
    private String strYoutube;


    public List<Ingredient> getIngredientsList() {
        List<Ingredient> list = new ArrayList<>();

        // Check all 20 potential ingredients
        addIfValid(list, strIngredient1);
        addIfValid(list, strIngredient2);
        addIfValid(list, strIngredient3);
        addIfValid(list, strIngredient4);
        addIfValid(list, strIngredient5);
        addIfValid(list, strIngredient6);
        addIfValid(list, strIngredient7);
        addIfValid(list, strIngredient8);
        addIfValid(list, strIngredient9);
        addIfValid(list, strIngredient10);
        addIfValid(list, strIngredient11);
        addIfValid(list, strIngredient12);
        addIfValid(list, strIngredient13);
        addIfValid(list, strIngredient14);
        addIfValid(list, strIngredient15);
        addIfValid(list, strIngredient16);
        addIfValid(list, strIngredient17);
        addIfValid(list, strIngredient18);
        addIfValid(list, strIngredient19);
        addIfValid(list, strIngredient20);

        return list;
    }

    private void addIfValid(List<Ingredient> list, String ingredientName) {
        if (ingredientName != null && !ingredientName.trim().isEmpty()) {
            list.add(new Ingredient(ingredientName));
        }
    }

    protected Meal(Parcel in) {
        dateModified = in.readString();
        idMeal = in.readString();
        strArea = in.readString();
        strCategory = in.readString();
        strCreativeCommonsConfirmed = in.readValue(Object.class.getClassLoader());
        strImageSource = in.readValue(Object.class.getClassLoader());
        strIngredient1 = in.readString();
        strIngredient10 = in.readString();
        strIngredient11 = in.readString();
        strIngredient12 = in.readString();
        strIngredient13 = in.readString();
        strIngredient14 = in.readString();
        strIngredient15 = in.readString();
        strIngredient16 = in.readString();
        strIngredient17 = in.readString();
        strIngredient18 = in.readString();
        strIngredient19 = in.readString();
        strIngredient2 = in.readString();
        strIngredient20 = in.readString();
        strIngredient3 = in.readString();
        strIngredient4 = in.readString();
        strIngredient5 = in.readString();
        strIngredient6 = in.readString();
        strIngredient7 = in.readString();
        strIngredient8 = in.readString();
        strIngredient9 = in.readString();
        strInstructions = in.readString();
        strMeal = in.readString();
        strMealAlternate = in.readValue(Object.class.getClassLoader());
        strMealThumb = in.readString();
        strMeasure1 = in.readString();
        strMeasure10 = in.readString();
        strMeasure11 = in.readString();
        strMeasure12 = in.readString();
        strMeasure13 = in.readString();
        strMeasure14 = in.readString();
        strMeasure15 = in.readString();
        strMeasure16 = in.readString();
        strMeasure17 = in.readString();
        strMeasure18 = in.readString();
        strMeasure19 = in.readString();
        strMeasure2 = in.readString();
        strMeasure20 = in.readString();
        strMeasure3 = in.readString();
        strMeasure4 = in.readString();
        strMeasure5 = in.readString();
        strMeasure6 = in.readString();
        strMeasure7 = in.readString();
        strMeasure8 = in.readString();
        strMeasure9 = in.readString();
        strSource = in.readString();
        strTags = in.readString();
        strYoutube = in.readString();
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(dateModified);
        dest.writeString(idMeal);
        dest.writeString(strArea);
        dest.writeString(strCategory);
        dest.writeValue(strCreativeCommonsConfirmed);
        dest.writeValue(strImageSource);
        dest.writeString(strIngredient1);
        dest.writeString(strIngredient10);
        dest.writeString(strIngredient11);
        dest.writeString(strIngredient12);
        dest.writeString(strIngredient13);
        dest.writeString(strIngredient14);
        dest.writeString(strIngredient15);
        dest.writeString(strIngredient16);
        dest.writeString(strIngredient17);
        dest.writeString(strIngredient18);
        dest.writeString(strIngredient19);
        dest.writeString(strIngredient2);
        dest.writeString(strIngredient20);
        dest.writeString(strIngredient3);
        dest.writeString(strIngredient4);
        dest.writeString(strIngredient5);
        dest.writeString(strIngredient6);
        dest.writeString(strIngredient7);
        dest.writeString(strIngredient8);
        dest.writeString(strIngredient9);
        dest.writeString(strInstructions);
        dest.writeString(strMeal);
        dest.writeValue(strMealAlternate);
        dest.writeString(strMealThumb);
        dest.writeString(strMeasure1);
        dest.writeString(strMeasure10);
        dest.writeString(strMeasure11);
        dest.writeString(strMeasure12);
        dest.writeString(strMeasure13);
        dest.writeString(strMeasure14);
        dest.writeString(strMeasure15);
        dest.writeString(strMeasure16);
        dest.writeString(strMeasure17);
        dest.writeString(strMeasure18);
        dest.writeString(strMeasure19);
        dest.writeString(strMeasure2);
        dest.writeString(strMeasure20);
        dest.writeString(strMeasure3);
        dest.writeString(strMeasure4);
        dest.writeString(strMeasure5);
        dest.writeString(strMeasure6);
        dest.writeString(strMeasure7);
        dest.writeString(strMeasure8);
        dest.writeString(strMeasure9);
        dest.writeString(strSource);
        dest.writeString(strTags);
        dest.writeString(strYoutube);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Meal> CREATOR = new Creator<Meal>() {
        @Override
        public Meal createFromParcel(Parcel in) {
            return new Meal(in);
        }

        @Override
        public Meal[] newArray(int size) {
            return new Meal[size];
        }
    };
}