package com.example.desafioframework.data.repository

import com.example.desafioframework.data.model.Album
import com.example.desafioframework.data.model.Post
import com.example.desafioframework.data.model.ToDo
import com.example.desafioframework.data.service.AppService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.Response

class AppRepositoryImpl(private val service: AppService) : AppRepository {

    override suspend fun findAllToDos(): List<ToDo> {
        lateinit var response: Response<List<ToDo>>
        return try {
            withContext(Dispatchers.IO) {
                response = service.findAllToDos().execute()
            }
            if (response.isSuccessful) {
                response.body()!!
            } else {
                emptyList()
            }
        } catch (exception: Exception) {
            throw Exception(exception.message)
        }
    }

    override suspend fun findAllPosts(): List<Post> {
        lateinit var response: Response<List<Post>>
        return try {
            withContext(Dispatchers.IO) {
                response = service.findAllPosts().execute()
            }
            if (response.isSuccessful) {
                response.body()!!
            } else {
                emptyList()
            }
        } catch (exception: Exception) {
            throw Exception(exception.message)
        }
    }

    override suspend fun findAllAlbums(): List<Album> {
        lateinit var response: Response<List<Album>>
        return try {
            withContext(Dispatchers.IO) {
                response = service.findAllAlbums().execute()
            }
            if (response.isSuccessful) {
                response.body()!!
            } else {
                emptyList()
            }
        } catch (exception: Exception) {
            throw Exception(exception.message)
        }
    }
}
