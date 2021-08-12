package com.example.desafioframework.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.desafioframework.data.model.Post

@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(posts: List<Post>)

    @Query("SELECT * FROM Post")
    fun getAll(): LiveData<List<Post>>
}