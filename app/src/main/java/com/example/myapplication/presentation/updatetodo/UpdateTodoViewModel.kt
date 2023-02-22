package com.example.myapplication.presentation.updatetodo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.db.TodoDatabase
import com.example.myapplication.db.TodoDatabaseDao
import com.example.myapplication.db.TodoItem
import com.example.myapplication.db.TodoRepository
import kotlinx.coroutines.*

class UpdateTodoViewModel (val database: TodoDatabaseDao, application: Application): AndroidViewModel(application){

    private var viewModelJob = Job()

    private val uiscope= CoroutineScope(Dispatchers.Main+viewModelJob)

    var currTodo= MutableLiveData<TodoItem?>()

    val allTodos : LiveData<List<TodoItem>>
    private val repository: TodoRepository
    private val _navigator= MutableLiveData<Boolean>()
    val navigator: LiveData<Boolean>
        get()=_navigator
    var getNight:TodoItem?=null     //this is used in context switching as we cant assign night value in IO thread
    init{
        val dao= TodoDatabase.getInstance(application).todoDatabaseDao
        repository= TodoRepository(dao)
        allTodos=repository.allTodos
        _navigator.value=false
    }


    val _nightId=MutableLiveData<TodoItem>()
    val nightId:LiveData<TodoItem>
        get()=_nightId
    fun getArgs(id:Long){
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
            _navigator.value=true           //ready for navigation, we will aware the fragment about and so he will take appropriate action.
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