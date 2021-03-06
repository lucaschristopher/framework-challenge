package com.example.desafioframework.data.di

import com.example.desafioframework.core.Constants
import com.example.desafioframework.data.database.AppDatabase
import com.example.desafioframework.data.repository.AlbumRepository
import com.example.desafioframework.data.repository.PostRepository
import com.example.desafioframework.data.repository.ToDoRepository
import com.example.desafioframework.data.service.AppService
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DataModule {

    // Exposes the modules to be loaded in the App
    fun load() {
        loadKoinModules(networkModules() + repositoryModule() + daoModule())
    }

    // Koin module to provide some items of interest to us
    private fun networkModules(): Module {
        return module {
            single {
                val interceptor = HttpLoggingInterceptor()
                interceptor.level = HttpLoggingInterceptor.Level.BODY

                OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build()
            }

            single {
                GsonConverterFactory.create(GsonBuilder().create())
            }

            single {
                createService<AppService>(get(), get())
            }
        }
    }

    // Koin module to provide some items of interest to us
    private fun repositoryModule(): Module {
        return module {
            factory { ToDoRepository(context = androidContext(), dao = get(), service = get()) }
            factory { PostRepository(context = androidContext(), dao = get(), service = get()) }
            factory { AlbumRepository(context = androidContext(), dao = get(), service = get()) }
        }
    }

    private fun daoModule(): Module {
        return module {
            single { AppDatabase.getDatabase(androidContext()).toDoDao }
            single { AppDatabase.getDatabase(androidContext()).postDao }
            single { AppDatabase.getDatabase(androidContext()).albumDao }
        }
    }

    private inline fun <reified T> createService(
        client: OkHttpClient,
        factory: GsonConverterFactory
    ): T {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(factory)
            .build().create(T::class.java)
    }
}
