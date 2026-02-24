package com.example.foodplannerapp.presentation.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
public class CalendarDateModel {
    private final Date fullDate;
    private final String dayOfWeek;
    private final String dayNumber;
    @Setter
    private boolean isSelected;

    public CalendarDateModel(Date fullDate, String dayOfWeek, String dayNumber) {
        this.fullDate = fullDate;
        this.dayOfWeek = dayOfWeek;
        this.dayNumber = dayNumber;
        this.isSelected = false;
    }

}