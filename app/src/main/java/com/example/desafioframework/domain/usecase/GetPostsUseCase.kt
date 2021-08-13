package com.example.desafioframework.domain.usecase

import com.example.desafioframework.core.UseCase
import com.example.desafioframework.data.model.Post
import com.example.desafioframework.data.repository.AppRepository

class GetPostsUseCase(
    private val repository: AppRepository
) /*: UseCase.NoParam<List<Post>>()*/ {

    /*override*/ suspend fun execute(): List<Post> {
        return repository.findAllPosts()
    }
}
