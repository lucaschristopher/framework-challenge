package com.example.desafioframework.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.desafioframework.data.model.ToDo

@Dao
interface ToDoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(todos: List<ToDo>)

    @Query("SELECT * FROM ToDo")
    fun getAll(): LiveData<List<ToDo>>
}