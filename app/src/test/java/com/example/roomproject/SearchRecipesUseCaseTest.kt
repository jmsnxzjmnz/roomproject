package com.example.roomproject

import com.example.roomproject.application.usecase.SearchRecipesUseCase
import com.example.roomproject.domain.modelo.Ingrediente
import com.example.roomproject.domain.modelo.Receta
import com.example.roomproject.domain.repositorio.RecipeRepository
import com.example.roomproject.infraestructure.remote.models.RecetaApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class SearchRecipesUseCaseTest {

    private lateinit var searchRecipesUseCase: SearchRecipesUseCase

    @Mock
    private lateinit var recipeRepository: RecipeRepository

    @Before
    fun setUp() {
        searchRecipesUseCase = SearchRecipesUseCase(recipeRepository)
    }

    @Test
    fun `invoke returns mapped recipes from API when API call is successful`() = runTest {
        // Arrange
        val ingredient = "chicken"
        val apiKey = "testApiKey"
        val mockApiRecipes = listOf(
            RecetaApi(id = 1, title = "Chicken Curry", ingredients = null, image = "image1", imageType = "jpg"),
            RecetaApi(id = 2, title = "Chicken Soup", ingredients = null, image = "image2", imageType = "jpg")
        )
        whenever(recipeRepository.searchRecipes(ingredient, apiKey)).thenReturn(mockApiRecipes)

        // Act
        val result = searchRecipesUseCase.invoke(ingredient, apiKey)

        // Assert
        assertEquals(2, result.size)
        assertEquals("Chicken Curry", result[0].title)
        verify(recipeRepository).searchRecipes(ingredient, apiKey)
    }

    @Test
    fun `invoke returns recipes from Room when API call fails`() = runTest {
        // Simula que la llamada a la API lanza una excepción
        whenever(recipeRepository.searchRecipes(any(), any())).thenThrow(RuntimeException("API Error"))

        // Datos simulados del repositorio (RecetaRoom)
        val mockRoomRecipes = listOf(
            RecetaRoom(
                id = 1,
                title = "Local Chicken Curry",
                ingredients = listOf(
                    IngredienteRoom(name = "Chicken", quantity = "500.0"),
                    IngredienteRoom(name = "Curry", quantity = "100.0")
                ),
                image = "localImage1",
                imageType = "jpg"
            )
        )
        whenever(recipeRepository.getStoredRecipes()).thenReturn(mockRoomRecipes)

        // Resultado esperado después de transformar RecetaRoom a Receta
        val expectedRecipes = mockRoomRecipes.map { roomRecipe ->
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

        // Ejecuta el caso de uso
        val result = searchRecipesUseCase.invoke("chicken", "apiKey123")

        // Verifica que el caso de uso devuelve los datos esperados
        assertEquals(expectedRecipes, result)

        // Verifica que se llamó al método del repositorio para obtener recetas de Room
        verify(recipeRepository).getStoredRecipes()
    }

    @Test
    fun `invoke returns empty list when both API and Room fail`() = runTest {
        // Arrange
        val ingredient = "chicken"
        val apiKey = "testApiKey"
        whenever(recipeRepository.searchRecipes(ingredient, apiKey)).thenThrow(RuntimeException("API error"))
        whenever(recipeRepository.getStoredRecipes()).thenReturn(emptyList())

        // Act
        val result = searchRecipesUseCase.invoke(ingredient, apiKey)

        // Assert
        assertTrue(result.isEmpty())
        verify(recipeRepository).getStoredRecipes()
    }
}
