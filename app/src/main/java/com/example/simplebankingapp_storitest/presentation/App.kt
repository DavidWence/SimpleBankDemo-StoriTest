package com.example.simplebankingapp_storitest.presentation

import android.app.Application
import androidx.annotation.StringRes
import com.example.simplebankingapp_storitest.data.dataModule
import com.example.simplebankingapp_storitest.data.homeModule
import com.example.simplebankingapp_storitest.data.loginModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {

    companion object{
        private lateinit var instance: App

        fun getString(@StringRes stringId: Int) = instance.resources.getString(stringId)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        //koin
        startKoin {
            androidContext(this@App)
            modules(listOf(dataModule, loginModule, homeModule))
        }
    }

}