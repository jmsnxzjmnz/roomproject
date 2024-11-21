package com.example.roomproject

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recetas")
data class Receta(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val ingredientes: String,
    val instrucciones: String
)

/*
data class Autor(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String
)*/