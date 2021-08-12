package com.example.desafioframework.domain.di

import com.example.desafioframework.domain.usecase.GetAlbumsUseCase
import com.example.desafioframework.domain.usecase.GetPostsUseCase
import com.example.desafioframework.domain.usecase.GetToDoUseCase
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object DomainModule {

    // Exposes the modules to be loaded in the App
    fun load() {
        loadKoinModules(useCaseModule())
    }

    // Koin module to provide our Use Cases
    private fun useCaseModule(): Module {
        return module {
            factory {
                GetToDoUseCase(repository = get())
            }
            factory {
                GetAlbumsUseCase(repository = get())
            }
            factory {
                GetPostsUseCase(repository = get())
            }
        }
    }
}
