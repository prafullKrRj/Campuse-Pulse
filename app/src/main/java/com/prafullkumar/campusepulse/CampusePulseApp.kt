package com.prafullkumar.campusepulse

import android.app.Application
import com.prafullkumar.campusepulse.di.AdminModule
import com.prafullkumar.campusepulse.di.AdminModuleImpl
import com.prafullkumar.campusepulse.di.AppModule
import com.prafullkumar.campusepulse.di.AppModuleImpl
import com.prafullkumar.campusepulse.di.StudentModule
import com.prafullkumar.campusepulse.di.StudentModuleImpl
import com.prafullkumar.campusepulse.di.TeacherModule
import com.prafullkumar.campusepulse.di.TeacherModuleImpl

class CampusePulseApp : Application() {
    lateinit var appModule: AppModule
    lateinit var adminModule: AdminModule
    lateinit var studentModule: StudentModule
    lateinit var teacherModule: TeacherModule
    override fun onCreate() {
        super.onCreate()
        appModule = AppModuleImpl(this)
        adminModule = AdminModuleImpl(this)
        studentModule = StudentModuleImpl(this)
        teacherModule = TeacherModuleImpl(this)
    }
}