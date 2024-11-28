package com.example.roomproject.application.usecase

import com.example.roomproject.domain.modelo.Ingrediente
import com.example.roomproject.domain.modelo.Receta
import com.example.roomproject.domain.repositorio.RecipeRepository
import javax.inject.Inject

class GetStoredRecipesUseCase @Inject constructor(
    private val repository: RecipeRepository
) {
    suspend operator fun invoke(): List<Receta> {
        val storedRecetas = repository.getStoredRecipes()
        return storedRecetas.map { roomRecipe ->
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
