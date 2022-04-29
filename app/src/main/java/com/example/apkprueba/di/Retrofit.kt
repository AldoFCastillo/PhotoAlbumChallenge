package com.example.apkprueba.di

import com.example.apkprueba.services.WebService
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.facebook.stetho.okhttp3.StethoInterceptor

import java.util.concurrent.TimeUnit.SECONDS


val retrofitModule = module {

    single { provideRetrofit(get(), get()) }
    single { provideGson() }
    single { provideHttpClient() }
}


fun provideHttpClient(): OkHttpClient {

    return OkHttpClient.Builder()
            .readTimeout(30L, SECONDS)
            .connectTimeout(30L, SECONDS)
            .callTimeout(30L, SECONDS)
            .writeTimeout(30L, SECONDS)
            .addNetworkInterceptor(StethoInterceptor())
            .build()
}

fun provideGson(): GsonConverterFactory {

    var gsonBuilder = GsonBuilder()

    return GsonConverterFactory.create(gsonBuilder.create())

}

fun provideRetrofit(gsonConverterFactory: GsonConverterFactory, client: OkHttpClient): WebService {
    return Retrofit.Builder()
            .baseUrl("https://61967289af46280017e7e0c0.mockapi.io")//preferences.url)
            .client(client)
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(WebService::class.java)
}

