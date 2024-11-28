package com.example.roomproject.infraestructure.database.bd

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.roomproject.RecetaRoom
import com.example.roomproject.infraestructure.database.converters.IngredienteTypeConverter
import com.example.roomproject.recetasDAO

@Database(entities = [RecetaRoom::class], version = 4)
@TypeConverters(IngredienteTypeConverter::class)
abstract class AppDataBase : RoomDatabase() {
    abstract fun recetasDAO() : recetasDAO
}