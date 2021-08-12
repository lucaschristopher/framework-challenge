package com.example.desafioframework.data.service

import com.example.desafioframework.data.model.Album
import com.example.desafioframework.data.model.Post
import com.example.desafioframework.data.model.ToDo
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface AppService {

    @GET("todos")
    fun findAllToDos(): Call<List<ToDo>>

    @GET("posts")
    fun findAllPosts(): Call<List<Post>>

    @GET("albums")
    fun findAllAlbums(): Call<List<Album>>
}