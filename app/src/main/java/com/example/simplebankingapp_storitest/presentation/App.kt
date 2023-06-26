package com.example.simplebankingapp_storitest.presentation

import android.app.Application
import androidx.annotation.StringRes
import com.example.simplebankingapp_storitest.data.dataModule
import com.example.simplebankingapp_storitest.data.homeModule
import com.example.simplebankingapp_storitest.data.loginModule
import com.example.simplebankingapp_storitest.data.userInfoModule
import com.example.simplebankingapp_storitest.data.utils.PreferenceComponent_LocalPreferences
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
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

        //Inicialización de PreferenceRoom
        PreferenceComponent_LocalPreferences.init(this)
        //Preconfiguración de Firebase Realtime Database
        Firebase.database.setPersistenceEnabled(true)
        //Inicialización de Koin
        startKoin {
            androidContext(this@App)
            modules(listOf(dataModule, userInfoModule, loginModule, homeModule))
        }
    }

}