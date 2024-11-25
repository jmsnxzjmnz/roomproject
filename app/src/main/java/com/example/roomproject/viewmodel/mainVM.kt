package com.example.roomproject.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomproject.RecetaRoom

import com.example.roomproject.database.bd.DatabaseClient
import com.example.roomproject.recetasDAO
import com.example.roomproject.remote.api.ApiClient
import com.example.roomproject.remote.models.RecetaApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(context : Context) : ViewModel() {

    // Obtengo el DAO usando DatabaseClient

    private val dao: recetasDAO = DatabaseClient.getDataBase(context = context).recetasDAO()

    private val _recetasApi = MutableStateFlow<List<RecetaApi>>(emptyList())

    val recetasapi: StateFlow<List<RecetaApi>> = _recetasApi

    private val _recetasRoom = MutableStateFlow<List<RecetaRoom>>(emptyList())

    val recerasroom: StateFlow<List<RecetaRoom>> = _recetasRoom

    fun searchRecipes(ingredient: String) {
        val apiKey = " " // Reemplaza con tu clave de API
        ApiClient.api.searchRecipes(ingredient, 10, apiKey).enqueue(object : Callback<List<RecetaApi>> {
            override fun onResponse(call: Call<List<RecetaApi>>, response: Response<List<RecetaApi>>) {
                if (response.isSuccessful) {
                    // Aquí manejamos la respuesta exitosa, que ya es una lista de recetas
                    Log.d("API_SUCCESS", "Recetas obtenidas: ${response.body()}")
                    _recetasApi.value = response.body() ?: emptyList()
                    val recetasApi = response.body() ?: emptyList()
                    val recetasRoom = recetasApi.map { apiReceta ->
                        RecetaRoom(
                            id = apiReceta.id,
                            title = apiReceta.title,
                            image = apiReceta.image,
                            imageType = apiReceta.imageType
                        )
                    }
                    // Guardar en Room
                    viewModelScope.launch {
                        dao.insertRecetas(recetasRoom)
                    }
                } else {
                    // Manejo de error si la respuesta no es exitosa
                    Log.e("API_ERROR", "Código de respuesta: ${response.code()} - Mensaje: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<RecetaApi>>, t: Throwable) {
                // Manejo de errores de red u otros problemas con la petición
                Log.e("API_FAILURE", "Error al realizar la petición: ${t.message}", t)
            }
        })
    }
    init {
        loadRecetas()
    }

    private fun loadRecetas() {
        viewModelScope.launch {
            _recetasRoom.value = dao.getAllRecetas() // Cambia este método según tu DAO
        }
    }
}