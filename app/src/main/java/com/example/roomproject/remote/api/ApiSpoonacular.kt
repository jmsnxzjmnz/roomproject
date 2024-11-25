package com.example.roomproject.remote.api

import com.example.roomproject.remote.models.RecetaApi
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiSpoonacular {
    @GET("recipes/findByIngredients")
     fun searchRecipes(
        @Query("ingredients") ingredients: String,
        @Query("number") number: Int,
        @Query("apiKey") apiKey: String
     ): Call<List<RecetaApi>>
}