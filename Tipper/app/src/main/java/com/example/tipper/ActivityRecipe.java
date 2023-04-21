package com.example.tipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.model.Recipe;
import com.example.utils.RecipeUtil;

import java.util.List;
/**
 * Author Michał Wadas s20495
 */

public class ActivityRecipe  extends AppCompatActivity {

    private RecipeListAdapter recipeAdapter;
    private RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        recycler = findViewById(R.id.recipe_recycler_view);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        List<Recipe> recipeList = RecipeUtil.loadRecipesFromFile(this, "recipes.json");
        if (recipeList != null) {
            recipeAdapter = new RecipeListAdapter(recipeList);
            recycler.setAdapter(recipeAdapter);
        }
    }
}