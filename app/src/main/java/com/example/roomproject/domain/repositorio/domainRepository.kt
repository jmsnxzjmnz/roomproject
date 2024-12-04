package com.example.roomproject.domain.repositorio

import android.util.Log
import com.example.roomproject.RecetaRoom
import com.example.roomproject.domain.modelo.Ingrediente
import com.example.roomproject.domain.modelo.Receta
import com.example.roomproject.recetasDAO
import com.example.roomproject.infraestructure.remote.api.ApiSpoonacular
import com.example.roomproject.infraestructure.remote.models.RecetaApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface RecipeRepository {
    suspend fun searchRecipes(ingredient: String, apiKey: String): List<RecetaApi>
    suspend fun getStoredRecipes(): List<RecetaRoom>
    suspend fun deleteAllRecipes()
    suspend fun getRandomRecipe( includeNutrition: Boolean,
                                 includeTags: String?,
                                 excludeTags: String?,
                                 number: Int,
                                 apiKey: String) : RecetaApi
}
class RecipeRepositoryImpl(
    private val api: ApiSpoonacular,
    private val recipeDao: recetasDAO
) : RecipeRepository {
    override suspend fun searchRecipes(ingredient: String, apiKey: String): List<RecetaApi> {
        return api.searchRecipes(ingredient, 10, apiKey) // Devuelve la respuesta directamente
    }

    override suspend fun getStoredRecipes(): List<RecetaRoom> {
        return recipeDao.getAllRecetas() // Devuelve directamente desde Room
    }

    override suspend fun deleteAllRecipes() {
        recipeDao.deleteAllRecetas()
    }

    override suspend fun getRandomRecipe(includeNutrition: Boolean,
                                         includeTags: String?,
                                         excludeTags: String?,
                                         number: Int,
                                         apiKey: String): RecetaApi{
        return api.getRandomRecipe(includeNutrition,includeTags,excludeTags,1,apiKey)
    }
}