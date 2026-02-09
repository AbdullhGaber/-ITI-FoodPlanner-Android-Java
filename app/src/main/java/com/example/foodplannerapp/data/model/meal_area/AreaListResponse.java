package com.example.foodplannerapp.data.model.meal_area;

import com.google.gson.annotations.SerializedName;
import java.util.List;

import lombok.Getter;

@Getter
public class AreaListResponse {
    @SerializedName("meals")
    private List<Area> areas;

}