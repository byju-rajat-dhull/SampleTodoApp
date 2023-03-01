package com.example.myapplication.presentation

import android.app.Application
import com.example.myapplication.data.api.TodoDatabaseDao
import com.example.myapplication.data.db.TodoDatabase
import com.example.myapplication.data.repository.TodoRepositoryImplementation

object Provider {

    lateinit var application: Application

    fun init(_application:Application){
        application= _application
    }
    val database: TodoDatabaseDao
        get() {
            return TodoDatabase.getInstance(application).todoDatabaseDao
        }
    val repo:TodoRepositoryImplementation
        get() {
            return TodoRepositoryImplementation(database)
        }
}