package com.example.roomproject.database.bd

import android.content.Context
import androidx.room.Room

object DatabaseClient {
    private var appDataBase: AppDataBase? = null

    fun getDataBase(context: Context): AppDataBase{
        if (appDataBase == null) {
            appDataBase = Room.databaseBuilder(
                context.applicationContext,
                AppDataBase::class.java,
                "app_database"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
        return appDataBase!!
    }
}