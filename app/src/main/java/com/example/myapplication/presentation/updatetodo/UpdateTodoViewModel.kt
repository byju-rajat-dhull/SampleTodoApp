package com.example.myapplication.presentation.updatetodo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.data.db.TodoDatabase
import com.example.myapplication.data.api.TodoDatabaseDao
import com.example.myapplication.data.db.TodoItem
import com.example.myapplication.data.repository.TodoRepositoryImplementation
import kotlinx.coroutines.*

class UpdateTodoViewModel (val database: TodoDatabaseDao, application: Application): AndroidViewModel(application){

    private var viewModelJob = Job()

    private val uiscope= CoroutineScope(Dispatchers.Main+viewModelJob)

    private val repository: TodoRepositoryImplementation      //The repo where all required functions are implemented

    //navigator and _navigator are basically used as flags
    // for letting the fragment know when to navigate and Encapsulation purpose
    private val _navigator= MutableLiveData<Boolean>()
    val navigator: LiveData<Boolean>
        get()=_navigator

    var getNight: TodoItem?=null     //this is used in context switching as we cant assign night value in IO thread

    init{       //initializer
        val dao= TodoDatabase.getInstance(application).todoDatabaseDao
        repository= TodoRepositoryImplementation(dao)
        _navigator.value=false
    }

    //This is to provide the fragment with the correct Todoitem
    // so that he can set the edittexts for title and description
    private val _nightId=MutableLiveData<TodoItem>()
    val nightId:LiveData<TodoItem>
        get()=_nightId
    fun getArgs(id:Long){           //Here we are getting the clicked todoitem's details
        uiscope.launch {
            withContext(Dispatchers.IO){
                getNight=repository.get(id)
            }
            _nightId.value=getNight
        }
    }

    fun updateTodo(todo: TodoItem){
        uiscope.launch {
            withContext(Dispatchers.IO){
                repository.update(todo)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}