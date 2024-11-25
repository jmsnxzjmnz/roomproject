package com.example.roomproject.remote.models

/*data class RecetaRespuesta(
    val results: List<com.example.roomproject.Receta>
)*/

data class RecetaApi(
    val id: Int,            // ID único de la receta
    val title: String,      // Título de la receta
    val image: String,      // URL de la imagen de la receta
    val imageType: String?  // Tipo de imagen (opcional)
)