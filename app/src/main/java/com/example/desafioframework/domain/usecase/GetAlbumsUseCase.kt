package com.example.desafioframework.domain.usecase

import com.example.desafioframework.core.UseCase
import com.example.desafioframework.data.model.Album
import com.example.desafioframework.data.repository.AppRepository

class GetAlbumsUseCase(
    private val repository: AppRepository
) /*: UseCase.NoParam<List<Album>>()*/ {

    /*override*/ suspend fun execute(): List<Album> {
        return repository.findAllAlbums()
    }
}
