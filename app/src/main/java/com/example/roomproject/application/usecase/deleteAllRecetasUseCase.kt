package com.example.roomproject.application.usecase

import com.example.roomproject.domain.repositorio.RecipeRepository
import javax.inject.Inject

class DeleteAllRecipesUseCase @Inject constructor(
    private val repository: RecipeRepository
) {
    suspend operator fun invoke() {
        repository.deleteAllRecipes()
    }
}
