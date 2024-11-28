package com.example.roomproject

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface recetasDAO {

    @Insert // Para insertar una receta en la base de datos
    suspend fun insert(recetaRoom: RecetaRoom)

    @Insert // Para insertar una receta en la base de datos
    suspend fun insertRecetas(recetaRoom: List<RecetaRoom>)

    @Query("SELECT * FROM recetas") // // Select para obtener todas las recetas
    suspend fun getAllRecetas(): List<RecetaRoom>

    @Query("SELECT * FROM recetas WHERE id = :id") // Select para obtener receta por id
    suspend fun getRecetaById(id: Int): RecetaRoom

    @Query("DELETE FROM recetas")
    suspend fun deleteAllRecetas()


}