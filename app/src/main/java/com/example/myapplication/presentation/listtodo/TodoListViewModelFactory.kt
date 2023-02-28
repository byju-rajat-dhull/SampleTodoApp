package com.example.myapplication.presentation.listtodo

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.api.TodoDatabaseDao

class TodoListViewModelFactory (private val dataSource: TodoDatabaseDao, private val application: Application) : ViewModelProvider.Factory{

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(TodoListViewModel::class.java)){
                return TodoListViewModel(dataSource,application) as T
            }
            throw java.lang.IllegalArgumentException("Unknown ViewModel")
        }
    }
