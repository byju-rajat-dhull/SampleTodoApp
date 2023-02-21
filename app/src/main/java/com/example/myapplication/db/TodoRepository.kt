package com.example.myapplication.db

import androidx.lifecycle.LiveData

class TodoRepository (private val todoDatabaseDao:TodoDatabaseDao){

    val allTodos: LiveData<List<TodoItem>> = todoDatabaseDao.getAll()

    suspend fun insert(todo:TodoItem){
        todoDatabaseDao.insert(todo)
    }

    suspend fun delete(todo:TodoItem){
        todoDatabaseDao.insert(todo)
    }

    suspend fun update(todo: TodoItem){
        todoDatabaseDao.update(todo)
    }

}