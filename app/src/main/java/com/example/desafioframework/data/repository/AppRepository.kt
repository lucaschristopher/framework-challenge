package com.example.desafioframework.data.repository

import com.example.desafioframework.data.model.Album
import com.example.desafioframework.data.model.Post
import com.example.desafioframework.data.model.ToDo

interface AppRepository {

    suspend fun findAllToDos(): List<ToDo>

    suspend fun findAllPosts(): List<Post>

    suspend fun findAllAlbums(): List<Album>
}