package com.example.myapplication.presentation.addtodo

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.db.TodoDatabaseDao

class AddTodoViewModelFactory(private val dataSource:TodoDatabaseDao, private val application: Application) : ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AddTodoViewModel::class.java)){
            return AddTodoViewModel(dataSource,application) as T
        }
        throw java.lang.IllegalArgumentException("Unknown ViewModel")
    }
}