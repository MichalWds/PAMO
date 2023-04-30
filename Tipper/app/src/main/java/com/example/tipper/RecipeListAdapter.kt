package com.example.tipper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.model.Recipe

/**
 * Author Michał Wadas s20495
 */
class RecipeListAdapter(private val recipeList: List<Recipe>) : RecyclerView.Adapter<RecipeListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = recipeList[position]
        holder.title.text = recipe.title
        holder.ingredients.text = recipe.ingredients
        holder.instructions.text = recipe.instructions
    }

    override fun getItemCount(): Int {
        return recipeList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView
        var ingredients: TextView
        var instructions: TextView

        init {
            title = itemView.findViewById(R.id.recipe_title)
            ingredients = itemView.findViewById(R.id.recipe_ingredients)
            instructions = itemView.findViewById(R.id.recipe_instructions)
        }
    }
}