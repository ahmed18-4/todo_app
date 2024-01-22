package com.example.todoapp.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val id:Int? = null,
    @ColumnInfo
    var title:String? = null,
    @ColumnInfo
    var descrption:String? = null,
    @ColumnInfo
    var date: Date ,
    @ColumnInfo
    var isDone:Boolean?= false
)
