package com.example.roomproject.database.bd

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.roomproject.Receta
import com.example.roomproject.recetasDAO

@Database(entities = [Receta::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun recetasDAO() : recetasDAO
}