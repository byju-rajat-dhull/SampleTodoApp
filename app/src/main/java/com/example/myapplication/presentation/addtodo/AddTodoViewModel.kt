package com.example.myapplication.presentation.addtodo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.db.TodoDatabase
import com.example.myapplication.data.api.TodoDatabaseDao
import com.example.myapplication.data.db.TodoItem
import com.example.myapplication.data.repository.TodoRepositoryImplementation
import com.example.myapplication.presentation.Provider
import kotlinx.coroutines.*

class AddTodoViewModel: ViewModel(){

    private var viewModelJob = Job()
    private val uiscope=CoroutineScope(Dispatchers.Main+viewModelJob)
    val allTodos : LiveData<List<TodoItem>>
    private val repo=Provider.repo
    init{
        allTodos=repo.allTodos
    }

    fun addTodo(todo: TodoItem){
        uiscope.launch {
            withContext(Dispatchers.IO){
                repo.insert(todo)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}