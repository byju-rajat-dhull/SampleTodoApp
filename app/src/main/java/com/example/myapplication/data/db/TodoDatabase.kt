package com.example.myapplication.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.data.api.TodoDatabaseDao

@Database(entities =[TodoItem::class], version = 1, exportSchema = false)
abstract class TodoDatabase:RoomDatabase() {

    abstract val todoDatabaseDao : TodoDatabaseDao

    companion object{

        @Volatile
        private var INSTANCE: TodoDatabase? =null
        fun getInstance(context:Context): TodoDatabase {

            synchronized(this){
                var instance = INSTANCE

                if(instance==null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TodoDatabase::class.java,
                        "MyTodoDB"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE =instance
                }
                return instance
            }
        }
    }
}