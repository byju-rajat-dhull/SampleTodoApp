package com.example.myapplication.presentation.addtodo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.data.db.TodoDatabase
import com.example.myapplication.data.api.TodoDatabaseDao
import com.example.myapplication.data.db.TodoItem
import com.example.myapplication.data.repository.TodoRepositoryImplementation
import kotlinx.coroutines.*

class AddTodoViewModel(val database: TodoDatabaseDao, application: Application):AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiscope=CoroutineScope(Dispatchers.Main+viewModelJob)
    val allTodos : LiveData<List<TodoItem>>
    private val repository: TodoRepositoryImplementation

    init{
        val dao= TodoDatabase.getInstance(application).todoDatabaseDao
        repository= TodoRepositoryImplementation(dao)
        allTodos=repository.allTodos
    }

    fun addTodo(todo: TodoItem){
        uiscope.launch {
            withContext(Dispatchers.IO){
                repository.insert(todo)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}