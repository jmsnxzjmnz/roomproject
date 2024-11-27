package com.example.roomproject.di

import android.content.Context
import androidx.room.Room
import com.example.roomproject.database.bd.AppDataBase
import com.example.roomproject.recetasDAO
import com.example.roomproject.remote.api.ApiClient
import com.example.roomproject.remote.api.ApiSpoonacular
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // Hace que las dependencias vivan mientras la aplicación esté en ejecución
object AppModule {

    private const val BASE_URL = "https://api.spoonacular.com/"

    // Proveer la instancia de Room Database
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDataBase {
        return Room.databaseBuilder(
            context,
            AppDataBase::class.java,
            "app_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    // Proveer el DAO de Room
    @Provides
    fun provideRecetasDAO(database: AppDataBase): recetasDAO {
        return database.recetasDAO()
    }

    // Proveer el cliente Retrofit
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL) // Cambia por tu base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Proveer el servicio de la API
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiSpoonacular {
        return retrofit.create(ApiSpoonacular::class.java)
    }

    @Provides
    @Singleton
    fun provideApiClient(): ApiClient {
        return ApiClient(BASE_URL)
    }
}
