package com.example.apkprueba.di

import com.example.apkprueba.services.Repository
import com.example.apkprueba.view.MainViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { Repository(get()) }
    viewModel { MainViewModel(get()) }
}