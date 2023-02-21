package com.example.myapplication.presentation.listtodo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.db.TodoDatabaseDao
import com.example.myapplication.db.TodoDatabase
import com.example.myapplication.db.TodoItem
import com.example.myapplication.db.TodoRepository
import kotlinx.coroutines.*

class TodoListViewModel(val database: TodoDatabaseDao, application: Application): AndroidViewModel(application)  {


    private var viewModelJob = Job()

    private val uiscope= CoroutineScope(Dispatchers.Main+viewModelJob)

    val allTodos : LiveData<List<TodoItem>>
    private val repository:TodoRepository
    private val _navigator= MutableLiveData<Boolean>()
    val navigator: LiveData<Boolean>
        get()=_navigator

    init{
        _navigator.value=false
        val dao=TodoDatabase.getInstance(application).todoDatabaseDao
        repository=TodoRepository(dao)
        allTodos=repository.allTodos
    }


    fun addTodo(todo:TodoItem){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                repository.insert(todo)
            }
        }
    }
    fun updateTodo(todo:TodoItem){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                repository.update(todo)
            }
        }
    }
    fun delTodo(todo:TodoItem){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                repository.delete(todo)
            }
        }
    }


    fun navigate(){
        _navigator.value=true
    }

    fun hasFinishedNav(){
        _navigator.value=false
    }
    var todos=database.getAll()
}