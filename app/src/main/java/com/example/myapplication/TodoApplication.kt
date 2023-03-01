package com.example.myapplication

import android.app.Application
import com.example.myapplication.presentation.Provider
import com.example.myapplication.presentation.Provider.application

class TodoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Provider.init(this)
    }
}