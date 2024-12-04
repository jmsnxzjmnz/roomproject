package com.example.roomproject.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomproject.application.usecase.DeleteAllRecipesUseCase
import com.example.roomproject.application.usecase.GetRandomRecipeUseCase
import com.example.roomproject.application.usecase.GetStoredRecipesUseCase
import com.example.roomproject.application.usecase.SearchRecipesUseCase

import com.example.roomproject.domain.modelo.Receta
import com.example.roomproject.domain.repositorio.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Thread.State
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val searchRecipesUseCase: SearchRecipesUseCase,
    private val getStoredRecipesUseCase: GetStoredRecipesUseCase,
    private val deleteAllRecipesUseCase: DeleteAllRecipesUseCase,
    private val getRandomRecipeUseCase: GetRandomRecipeUseCase
) : ViewModel() {

    private val _recetaRandom = MutableStateFlow<Receta?>(null)
    val recetaRandom : StateFlow<Receta?> = _recetaRandom

    private val _recipes = MutableStateFlow<List<Receta>>(emptyList())
    val recipes: StateFlow<List<Receta>> = _recipes

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        loadStoredRecipes() // Carga inicial de recetas desde Room
    }

    /**
     * Busca recetas por un ingrediente y las guarda en Room
     */
    fun searchRecipes(ingredient: String) {
        viewModelScope.launch {
            try {
                _recipes.value = searchRecipesUseCase(ingredient, "0d08be64ecd94bbe810bfe4486df1187")
            } catch (e: Exception) {
                Log.e("VM_ERROR", "Error buscando recetas: ${e.message}", e)
            }
        }
    }

    fun deleteAllRecipes() {
        viewModelScope.launch {
            try {
                deleteAllRecipesUseCase()
                Log.d("ROOM_SUCCESS", "Recetas eliminadas")
                loadStoredRecipes() // Recarga la lista tras el borrado
            }
            catch (e: java.lang.Exception) {
                Log.e("ROOM_ERROR", "NO SE HAN PODIDO BORRAR")
            }
        }
    }
    fun obtenerRecetaRandom(){
        viewModelScope.launch {
            try {
              _recetaRandom.value = getRandomRecipeUseCase(false,"","","0d08be64ecd94bbe810bfe4486df1187")
                Log.d("API SUCCESS","Receta obtenida")
                recetaRandom.value?.let { _recipes.value.toMutableList().add(0, it) }
                loadStoredRecipes() // Recarga la lista
            } catch (e: java.lang.Exception){
                Log.e("VM_ERROR", "Error buscando receta" )
            }
        }
    }

    /**
     * Carga recetas almacenadas en Room
     */
    private fun loadStoredRecipes() {
        viewModelScope.launch {
            try {
                _recipes.value = getStoredRecipesUseCase()
                Log.d("ROOM_SUCCESS", "Recetas cargadas desde Room: $recipes")
            } catch (e: Exception) {
                Log.e("ROOM_ERROR", "Error al cargar recetas desde Room: ${e.message}", e)
                _error.value = "Error al cargar recetas almacenadas: ${e.message}"
            }
        }
    }
}