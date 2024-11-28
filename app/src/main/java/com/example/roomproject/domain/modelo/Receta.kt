package com.example.roomproject.domain.modelo

data class Receta(
    val id: Int,
    val title: String,
    val ingredients: List<Ingrediente>, // Clase de dominio para ingredientes
    val image: String,
    val imageType: String?
)

data class Ingrediente(
    val name: String,
    val quantity: String
)