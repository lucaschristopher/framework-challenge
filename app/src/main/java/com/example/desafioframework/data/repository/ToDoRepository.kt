package com.example.desafioframework.data.repository

import android.content.Context
import com.example.desafioframework.core.AppState
import com.example.desafioframework.core.Utils
import com.example.desafioframework.core.noNetworkConnectivityError
import com.example.desafioframework.data.dao.ToDoDao
import com.example.desafioframework.data.model.Album
import com.example.desafioframework.data.model.Post
import com.example.desafioframework.data.model.ToDo
import com.example.desafioframework.data.service.AppService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class ToDoRepository(
    private val context: Context,
    private val dao: ToDoDao,
    private val service: AppService
) : AppRepository {

    override suspend fun findAllToDos(): AppState<List<ToDo>> {
        lateinit var response: Response<List<ToDo>>

        // Request webservice
        if (Utils.isOnline(context)) {
            return try {
                withContext(Dispatchers.IO) {
                    response = service.findAllToDos().execute()
                }
                if (response.isSuccessful) {
                    response.body()?.let {
                        withContext(Dispatchers.IO) {
                            dao.save(it)
                        }
                    }
                    Utils.handleSuccess(response)
                } else {
                    Utils.handleApiError(response)
                }
            } catch (exception: Exception) {
                throw Exception(exception.message)
            }
        } else {
            // Get data from cache
            val data = this.getToDosDataFromCache()
            return if (data.isNotEmpty()) {
                AppState.Success(data)
            } else {
                context.noNetworkConnectivityError()
            }
        }
    }

    private suspend fun getToDosDataFromCache(): List<ToDo> {
        return withContext(Dispatchers.IO) {
            dao.getAll()
        }
    }

    override suspend fun findAllPosts(): AppState<List<Post>> {
        TODO("Not yet implemented")
    }

    override suspend fun findAllAlbums(): AppState<List<Album>> {
        TODO("Not yet implemented")
    }
}