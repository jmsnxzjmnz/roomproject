package com.example.roomproject.infraestructure.database.converters

import androidx.room.TypeConverter
import com.example.roomproject.IngredienteRoom
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class IngredienteTypeConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromIngredienteList(ingredientes: List<IngredienteRoom>?): String {
        return gson.toJson(ingredientes)
    }

    @TypeConverter
    fun toIngredienteList(data: String?): List<IngredienteRoom>? {
        if (data.isNullOrEmpty()) return emptyList()
        val listType = object : TypeToken<List<IngredienteRoom>>() {}.type
        return gson.fromJson(data, listType)
    }
}