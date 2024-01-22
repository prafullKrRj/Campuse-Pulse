package com.prafullkumar.campusepulse

import android.app.Application
import com.prafullkumar.campusepulse.di.AdminModule
import com.prafullkumar.campusepulse.di.AdminModuleImpl
import com.prafullkumar.campusepulse.di.AppModule
import com.prafullkumar.campusepulse.di.AppModuleImpl

class CampusePulseApp : Application() {
    lateinit var appModule: AppModule
    lateinit var adminModule: AdminModule
    override fun onCreate() {
        super.onCreate()
        appModule = AppModuleImpl(this)
        adminModule = AdminModuleImpl(this)
    }
}