package com.example.tipper

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.utils.RecipeUtil

/**
 * Author Michał Wadas s20495
 */
class ActivityRecipe : AppCompatActivity() {
    private var recipeAdapter: RecipeListAdapter? = null
    private var recycler: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)
        recycler = findViewById(R.id.recipe_recycler_view)
        recycler?.layoutManager = LinearLayoutManager(this)
        val recipeList = RecipeUtil.loadRecipesFromFile(this, "recipes.json")
        if (recipeList != null) {
            recipeAdapter = RecipeListAdapter(recipeList)
            recycler?.adapter = recipeAdapter
        }
    }
}