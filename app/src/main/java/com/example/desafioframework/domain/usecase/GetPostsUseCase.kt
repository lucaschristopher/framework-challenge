package com.example.desafioframework.domain.usecase

import com.example.desafioframework.core.AppState
import com.example.desafioframework.core.UseCase
import com.example.desafioframework.data.model.Post
import com.example.desafioframework.data.repository.PostRepository

class GetPostsUseCase(
    private val repository: PostRepository
) : UseCase.NoParam<AppState<List<Post>>>() {

    override suspend fun execute(): AppState<List<Post>> {
        return repository.findAllPosts()
    }
}
