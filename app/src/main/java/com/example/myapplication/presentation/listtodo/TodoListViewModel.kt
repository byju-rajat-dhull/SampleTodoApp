package com.example.myapplication.presentation.listtodo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.api.TodoDatabaseDao
import com.example.myapplication.data.db.TodoDatabase
import com.example.myapplication.data.db.TodoItem

import com.example.myapplication.presentation.Provider
import kotlinx.coroutines.*


class TodoListViewModel: ViewModel() {


    val allTodos : LiveData<List<TodoItem>>
    private val repo=Provider.repo
    init{
//        _navigator.value=false
//        val dao= TodoDatabase.getInstance(application).todoDatabaseDao
//        val dao=Provider.database
//        repository= TodoRepositoryImplementation(dao)
        allTodos=repo.allTodos
    }

    fun delTodo(todo: TodoItem){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
//                Log.i("todolvm","deleting scope came")
                repo.delete(todo)
//                Log.i("todolvm","deletion done")
            }
        }
    }




//    private val _updateId=MutableLiveData<Long?>()
//    val updateId:LiveData<Long?>
//        get()=_updateId
//    fun onTodoClicked(id:Long){
//        _updateId.value=id
//    }
//    fun onTodoNavigated(){
//        _updateId.value=null
//    }


//    private val _delId=MutableLiveData<TodoItem?>()
//    val delId:LiveData<TodoItem?>
//        get()=_delId
//    fun onDelClicked(todoItem: TodoItem){
//        _delId.value=todoItem
//    }
}