package com.example.roomproject.database.bd

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.roomproject.RecetaRoom
import com.example.roomproject.recetasDAO

@Database(entities = [RecetaRoom::class], version = 2)
abstract class AppDataBase : RoomDatabase() {
    abstract fun recetasDAO() : recetasDAO
}