package com.example.utils

import android.content.Context
import com.example.model.Recipe
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

/**
 * Author Michał Wadas s20495
 */
object RecipeUtil {
    fun loadRecipesFromFile(context: Context, fileName: String?): List<Recipe>? {
        return try {
            val assetManager = context.assets
            val inputStream = assetManager.open(fileName!!)
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))
            val gson = Gson()
            val listType = object : TypeToken<ArrayList<Recipe?>?>() {}.type
            gson.fromJson(bufferedReader, listType)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
}