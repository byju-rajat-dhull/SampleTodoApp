package com.example.myapplication.data.repository

import androidx.lifecycle.LiveData
import com.example.myapplication.data.api.TodoDatabaseDao
import com.example.myapplication.data.db.TodoItem
import com.example.myapplication.domain.repositoryInterface.TodoRepository

class TodoRepositoryImplementation (private val todoDatabaseDao: TodoDatabaseDao):TodoRepository{

    val allTodos: LiveData<List<TodoItem>> = todoDatabaseDao.getAll()

    override suspend fun insert(todo: TodoItem){
        todoDatabaseDao.insert(todo)
    }

    override suspend fun delete(todo: TodoItem){
        todoDatabaseDao.delete(todo)
    }

    override suspend fun update(todo: TodoItem){
        todoDatabaseDao.update(todo)
    }

    override suspend fun get(id:Long): TodoItem {
        return todoDatabaseDao.get(id)
    }

    override fun getAll(): LiveData<List<TodoItem>> {
        return todoDatabaseDao.getAll()
    }

    override suspend fun deleteAll(todos: List<TodoItem>): Int {
        return todoDatabaseDao.deleteAll(todos)
    }

}