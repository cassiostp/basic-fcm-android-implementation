package com.example.cassio.myapplication

import android.app.Application
import android.os.Build

class CustomApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setNotificationChannels()
    }

    fun setNotificationChannels(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationHelper = NotificationHelper(applicationContext)
            notificationHelper.createChannels()
        }
    }
}