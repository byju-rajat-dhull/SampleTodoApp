package com.example.myapplication.presentation.addtodo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.db.TodoDatabase
import com.example.myapplication.db.TodoDatabaseDao
import com.example.myapplication.db.TodoItem
import com.example.myapplication.db.TodoRepository
import kotlinx.coroutines.*

class AddTodoViewModel(val database: TodoDatabaseDao, application: Application):AndroidViewModel(application) {


    private var viewModelJob = Job()

    private val uiscope=CoroutineScope(Dispatchers.Main+viewModelJob)

    var currTodo= MutableLiveData<TodoItem?>()

    val allTodos : LiveData<List<TodoItem>>
    private val repository: TodoRepository
    private val _navigator= MutableLiveData<Boolean>()
    val navigator: LiveData<Boolean>
        get()=_navigator

    init{
        val dao= TodoDatabase.getInstance(application).todoDatabaseDao
        repository=TodoRepository(dao)
        allTodos=repository.allTodos
        _navigator.value=false
    }


//    fun updateTodo(todo:TodoItem){
//        viewModelScope.launch {
//            withContext(Dispatchers.IO){
//                repository.update(todo)
//            }
//        }
//    }
    fun addTodo(todo:TodoItem){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                repository.insert(todo)
            }
            _navigator.value=true
        }
    }



    fun hasFinishedNav(){
        _navigator.value=false
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}