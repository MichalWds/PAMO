package com.example.utils;
import android.content.Context;
import android.content.res.AssetManager;

import com.example.model.Recipe;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
/**
 * Author Michał Wadas s20495
 */

public class RecipeUtil {
    public static List<Recipe> loadRecipesFromFile(Context context, String fileName) {
        try {
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open(fileName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Recipe>>(){}.getType();
            List<Recipe> recipes = gson.fromJson(bufferedReader, listType);
            return recipes;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
