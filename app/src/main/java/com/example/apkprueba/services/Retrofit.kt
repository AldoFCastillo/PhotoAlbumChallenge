package com.example.apkprueba.services

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Retrofit {

    val webservice by lazy{

        Retrofit.Builder()
            .baseUrl("https://61967289af46280017e7e0c0.mockapi.io")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build().create(WebService::class.java)
    }

}