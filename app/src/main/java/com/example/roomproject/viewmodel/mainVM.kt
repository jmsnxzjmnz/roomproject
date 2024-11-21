package com.example.roomproject.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomproject.Receta
import com.example.roomproject.database.bd.DatabaseClient
import com.example.roomproject.recetasDAO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(context : Context) : ViewModel() {

    // Obtengo el DAO usando DatabaseClient

    private val dao: recetasDAO = DatabaseClient.getDataBase(context = context).recetasDAO()

    private val _recetas = MutableStateFlow<List<Receta>>(emptyList())
    val recetas: StateFlow<List<Receta>> = _recetas

    init {
        loadRecetas()
    }

    private fun loadRecetas() {
        viewModelScope.launch {
            _recetas.value = dao.getAllRecetas() // Cambia este método según tu DAO
        }
    }
}