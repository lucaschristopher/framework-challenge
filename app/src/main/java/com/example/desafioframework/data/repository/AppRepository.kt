package com.example.desafioframework.data.repository

import com.example.desafioframework.core.AppState
import com.example.desafioframework.data.model.Album
import com.example.desafioframework.data.model.Post
import com.example.desafioframework.data.model.ToDo

interface AppRepository {

    suspend fun findAllToDos(): AppState<List<ToDo>>

    suspend fun findAllPosts(): AppState<List<Post>>

    suspend fun findAllAlbums(): AppState<List<Album>>
}