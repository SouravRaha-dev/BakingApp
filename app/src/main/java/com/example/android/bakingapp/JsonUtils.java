package com.example.android.bakingapp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    public static List<Recipe> parseJson(String json) {
        Gson gson = new GsonBuilder().create();
        Recipe[] tabRecipes = gson.fromJson(json, Recipe[].class);
        List<Recipe> listRecipes = new ArrayList<>();
        for (int i = 0; i < tabRecipes.length; i++)
            listRecipes.add(tabRecipes[i]);
        return listRecipes;
    }
}
