package com.example.roomproject.application.usecase

import androidx.room.PrimaryKey
import com.example.roomproject.domain.modelo.Receta
import com.example.roomproject.domain.repositorio.RecipeRepository
import com.example.roomproject.infraestructure.remote.models.RecetaApi
import javax.inject.Inject

class GetRandomRecipeUseCase @Inject constructor(
    private val repository: RecipeRepository
) {
    suspend operator fun invoke(includeNutrition: Boolean = false,
                                includeTags: String? = null,
                                excludeTags: String? = null,
                                apiKey: String): Receta {
        return try {
            // Llama a la API para buscar una receta
            val recetaApi: RecetaApi = repository.getRandomRecipe(includeNutrition,includeTags,excludeTags,1,apiKey)

             Receta(
                id = recetaApi.id,
                title = recetaApi.title,
                ingredients = recetaApi.ingredients ?: emptyList(),
                image = recetaApi.image,
                imageType = recetaApi.imageType
            )

        }
        catch (e: Exception){
            throw RecipeFetchException("Error al obtener la receta: ${e.message}", e)
        }
    }
    class RecipeFetchException(message: String, cause: Throwable? = null) : Exception(message, cause)

}