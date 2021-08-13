package com.example.desafioframework.domain.usecase

import com.example.desafioframework.core.AppState
import com.example.desafioframework.core.UseCase
import com.example.desafioframework.data.model.Album
import com.example.desafioframework.data.repository.AlbumRepository

class GetAlbumsUseCase(
    private val repository: AlbumRepository
) : UseCase.NoParam<AppState<List<Album>>>() {

    override suspend fun execute(): AppState<List<Album>> {
        return repository.findAllAlbums()
    }
}
