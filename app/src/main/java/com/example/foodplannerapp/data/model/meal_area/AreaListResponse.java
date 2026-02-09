package com.example.foodplannerapp.data.model.meal_area;

import com.google.gson.annotations.SerializedName;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AreaListResponse {
    @SerializedName("meals")
    private List<Area> areas;

}