package com.example.myapplication.presentation.updatetodo

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.db.TodoDatabaseDao
import com.example.myapplication.presentation.listtodo.TodoListViewModel

class UpdateTodoViewModelFactory (private val dataSource: TodoDatabaseDao, private val application: Application) : ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(UpdateTodoViewModel::class.java)){
            return UpdateTodoViewModel(dataSource,application) as T
        }
        throw java.lang.IllegalArgumentException("Unknown ViewModel")
    }
}
