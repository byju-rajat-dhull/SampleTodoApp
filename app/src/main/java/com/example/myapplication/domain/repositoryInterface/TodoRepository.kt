package com.example.myapplication.domain.repositoryInterface

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.data.db.TodoItem

interface TodoRepository {

    suspend fun insert(todo: TodoItem)

    suspend fun update(todo: TodoItem)

    suspend fun get(id:Long): TodoItem

    fun getAll(): LiveData<List<TodoItem>>

    suspend fun deleteAll(todos:List<TodoItem>) : Int

    suspend fun delete(todo: TodoItem)

}