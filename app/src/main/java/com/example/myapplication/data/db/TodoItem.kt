package com.example.myapplication.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="MyTodo")
//@Entity
data class TodoItem(

    @PrimaryKey(autoGenerate = true)
    var todoID : Long = 0L,

    @ColumnInfo(name="title")
    var title : String = "",

    @ColumnInfo(name="desc")
    var description : String = "",

    @ColumnInfo(name="Time")
    var timestamp :String =""
)