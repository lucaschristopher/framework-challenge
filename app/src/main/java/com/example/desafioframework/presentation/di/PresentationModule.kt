package com.example.desafioframework.presentation.di

import com.example.desafioframework.presentation.ui.album.AlbumViewModel
import com.example.desafioframework.presentation.ui.post.PostViewModel
import com.example.desafioframework.presentation.ui.todo.ToDoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object PresentationModule {

    // Exposes the modules to be loaded in the App
    fun load() {
        loadKoinModules(viewModelModule())
    }

    private fun viewModelModule(): Module {
        return module {
            viewModel { ToDoViewModel(getToDoUseCase = get()) }
            viewModel { PostViewModel(getPostsUseCase = get()) }
            viewModel { AlbumViewModel(getAlbumsUseCase = get()) }
        }
    }
}
