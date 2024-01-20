package com.prafullkumar.campusepulse

import android.app.Application
import com.prafullkumar.campusepulse.di.AppModule
import com.prafullkumar.campusepulse.di.AppModuleImpl

class CampusePulseApp : Application() {
    lateinit var appContainer: AppModule
    override fun onCreate() {
        super.onCreate()
        appContainer = AppModuleImpl(this)
    }
}