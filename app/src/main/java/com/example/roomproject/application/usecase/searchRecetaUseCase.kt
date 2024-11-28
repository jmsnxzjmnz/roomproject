package com.example.roomproject.application.usecase

import com.example.roomproject.domain.modelo.Ingrediente
import com.example.roomproject.domain.modelo.Receta
import com.example.roomproject.domain.repositorio.RecipeRepository
import javax.inject.Inject

class SearchRecipesUseCase @Inject constructor(
    private val repository: RecipeRepository
) {
    suspend operator fun invoke(ingredient: String, apiKey: String): List<Receta> {
        return try {
            // Llama a la API para buscar recetas
            val recetasApi = repository.searchRecipes(ingredient, apiKey)
            // Transforma los datos de API a Receta (dominio)
            recetasApi.map { apiReceta ->
                Receta(
                    id = apiReceta.id,
                    title = apiReceta.title,
                    ingredients = apiReceta.ingredients ?: emptyList(),
                    image = apiReceta.image,
                    imageType = apiReceta.imageType
                )
            }
        } catch (e: Exception) {
            // Si falla la API, obtén recetas almacenadas en Room
            val roomRecetas = repository.getStoredRecipes()
            roomRecetas.map { roomRecipe ->
                Receta(
                    id = roomRecipe.id,
                    title = roomRecipe.title,
                    ingredients = roomRecipe.ingredients?.map { roomIngredient ->
                        Ingrediente(
                            name = roomIngredient.name,
                            quantity = roomIngredient.quantity
                        )
                    } ?: emptyList(),
                    image = roomRecipe.image,
                    imageType = roomRecipe.imageType
                )
            }
        }
    }
}