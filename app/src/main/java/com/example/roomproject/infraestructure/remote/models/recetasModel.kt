package com.example.roomproject.infraestructure.remote.models

import com.example.roomproject.IngredienteRoom
import com.example.roomproject.domain.modelo.Ingrediente

/*data class RecetaRespuesta(
    val results: List<com.example.roomproject.Receta>
)*/

data class RecetaApi(
    val id: Int,            // ID único de la receta
    val title: String, // Título de la receta
    val ingredients: List<Ingrediente>?,
    val image: String,      // URL de la imagen de la receta
    val imageType: String?  // Tipo de imagen (opcional)
)
/*
data class IngredienteApi(
    val name: String,
    val quantity: String
)
*/
