package com.example.foodplannerapp.data.model.meal_area;

import com.google.gson.annotations.SerializedName;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

@Getter
public class Area {
    @SerializedName("strArea")
    private String areaName;
    private static final Map<String, String> COUNTRY_CODES = new HashMap<>();

    static {
        COUNTRY_CODES.put("Algerian", "dz");
        COUNTRY_CODES.put("American", "us");
        COUNTRY_CODES.put("Argentinian", "ar");
        COUNTRY_CODES.put("British", "gb");
        COUNTRY_CODES.put("Canadian", "ca");
        COUNTRY_CODES.put("Chinese", "cn");
        COUNTRY_CODES.put("Croatian", "hr");
        COUNTRY_CODES.put("Cypriot", "cy");
        COUNTRY_CODES.put("Dutch", "nl");
        COUNTRY_CODES.put("Egyptian", "eg");
        COUNTRY_CODES.put("Filipino", "ph");
        COUNTRY_CODES.put("French", "fr");
        COUNTRY_CODES.put("German", "de");
        COUNTRY_CODES.put("Greek", "gr");
        COUNTRY_CODES.put("Indian", "in");
        COUNTRY_CODES.put("Indonesian", "id");
        COUNTRY_CODES.put("Irish", "ie");
        COUNTRY_CODES.put("Italian", "it");
        COUNTRY_CODES.put("Jamaican", "jm");
        COUNTRY_CODES.put("Japanese", "jp");
        COUNTRY_CODES.put("Kenyan", "ke");
        COUNTRY_CODES.put("Malaysian", "my");
        COUNTRY_CODES.put("Mexican", "mx");
        COUNTRY_CODES.put("Moroccan", "ma");
        COUNTRY_CODES.put("Norwegian", "no");
        COUNTRY_CODES.put("Polish", "pl");
        COUNTRY_CODES.put("Portuguese", "pt");
        COUNTRY_CODES.put("Russian", "ru");
        COUNTRY_CODES.put("Saudi Arabian", "sa");
        COUNTRY_CODES.put("Slovakian", "sk");
        COUNTRY_CODES.put("Spanish", "es");
        COUNTRY_CODES.put("Syrian", "sy");
        COUNTRY_CODES.put("Thai", "th");
        COUNTRY_CODES.put("Tunisian", "tn");
        COUNTRY_CODES.put("Turkish", "tr");
        COUNTRY_CODES.put("Ukrainian", "ua");
        COUNTRY_CODES.put("Uruguayan", "uy");
        COUNTRY_CODES.put("Venezuelan", "ve");
        COUNTRY_CODES.put("Vietnamese", "vn");
        COUNTRY_CODES.put("Unknown", "xk");
    }

    public String getCountryCode() {
        if (areaName == null) return "xk";
        return COUNTRY_CODES.getOrDefault(areaName, "xk");
    }

    public String getFlagUrl() {
        String code = getCountryCode();
        return "https://flagcdn.com/w80/" + code + ".png";
    }
}