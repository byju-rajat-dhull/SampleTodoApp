package com.example.myapplication.presentation.updatetodo

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

class UpdateTodoViewModel :ViewModel(){

    private var viewModelJob = Job()
    private val uiscope= CoroutineScope(Dispatchers.Main+viewModelJob)

    private val repo=Provider.repo

    var getNight: TodoItem?=null     //this is used in context switching as we cant assign night value in IO thread


    //This is to provide the fragment with the correct Todoitem
    // so that he can set the edittexts for title and description
    private val _nightId=MutableLiveData<TodoItem>()
    val nightId:LiveData<TodoItem>
        get()=_nightId
    fun getArgs(id:Long){           //Here we are getting the clicked todoitem's details
        uiscope.launch {
            withContext(Dispatchers.IO){
                getNight=repo.get(id)
            }
            _nightId.value=getNight
        }
    }

    fun updateTodo(todo: TodoItem){
        uiscope.launch {
            withContext(Dispatchers.IO){
                repo.update(todo)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}