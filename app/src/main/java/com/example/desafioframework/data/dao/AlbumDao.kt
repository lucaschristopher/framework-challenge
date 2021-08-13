package com.example.desafioframework.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.desafioframework.data.model.Album

@Dao
interface AlbumDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(albums: List<Album>)

    @Query("SELECT * FROM Album")
    fun getAll(): List<Album>
}