package com.prafullkumar.campusepulse

import android.app.Application
import com.prafullkumar.campusepulse.adminApp.di.AdminModule
import com.prafullkumar.campusepulse.adminApp.di.AdminModuleImpl
import com.prafullkumar.campusepulse.onBoard.di.AppModule
import com.prafullkumar.campusepulse.onBoard.di.AppModuleImpl
import com.prafullkumar.campusepulse.studentApp.di.StudentModule
import com.prafullkumar.campusepulse.studentApp.di.StudentModuleImpl
import com.prafullkumar.campusepulse.teacherApp.di.TeacherModule
import com.prafullkumar.campusepulse.teacherApp.di.TeacherModuleImpl

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