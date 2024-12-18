package com.example.roomproject.infraestructure.remote.api

import com.example.roomproject.infraestructure.remote.models.RecetaApi
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiSpoonacular {
    @GET("recipes/findByIngredients")
    suspend fun searchRecipes(

        @Query("ingredients") ingredients: String,
        @Query("number") number: Int,
        @Query("apiKey") apiKey: String
     ): List<RecetaApi>

    @GET("recipes/random")
    suspend fun getRandomRecipe(
        @Query("includeNutrition") includeNutrition: Boolean = false,
        @Query("include-tags") includeTags: String? = null,
        @Query("exclude-tags") excludeTags: String? = null,
        @Query("number") number: Int = 1,
        @Query("apiKey") apiKey: String
    ):  RecetaApi
}