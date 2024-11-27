package com.example.roomproject.remote.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient(base: String) {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(base)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: ApiSpoonacular = retrofit.create(ApiSpoonacular::class.java)
}