package com.example.roomproject

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recetas")
data class RecetaRoom(
    @PrimaryKey(autoGenerate = true)    val id: Int,            // ID único de la receta
    val title: String,      // Título de la receta
    val ingredients: List<IngredienteRoom>?,
    val image: String,      // URL de la imagen de la receta
    val imageType: String?  // Tipo de imagen (opcional)
)


data class IngredienteRoom(
     val  name : String,
    val quantity : String
)

/*
data class Autor(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String
)*/