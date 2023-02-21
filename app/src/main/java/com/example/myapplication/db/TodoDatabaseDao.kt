package com.example.myapplication.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TodoDatabaseDao {

    @Insert
    suspend fun insert(todo:TodoItem)

    @Update
    suspend fun update(todo: TodoItem)

    @Query("SELECT * from MyTodo WHERE todoID = :id")
    suspend fun get(id:Long):TodoItem

    @Query("SELECT * from MyTodo ORDER BY todoID DESC")
    fun getAll(): LiveData<List<TodoItem>>

    @Delete
    suspend fun deleteAll(todos:List<TodoItem>) : Int

    @Delete
    suspend fun delete(todo:TodoItem)

}