package com.example.apkprueba.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.apkprueba.R
import com.example.apkprueba.databinding.ActivityMainBinding
import com.example.apkprueba.di.appModule
import com.example.apkprueba.di.retrofitModule
import com.facebook.stetho.Stetho
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Stetho.initializeWithDefaults(this)

        ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }

        startKoin {
            androidLogger()
            androidContext(this@MainActivity)
            modules(listOf(appModule, retrofitModule))
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, MainFragment::class.java, null)
            .commit()
    }


}