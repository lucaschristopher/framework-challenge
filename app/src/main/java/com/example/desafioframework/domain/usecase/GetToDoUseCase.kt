package com.example.desafioframework.domain.usecase

import com.example.desafioframework.core.UseCase
import com.example.desafioframework.data.model.ToDo
import com.example.desafioframework.data.repository.AppRepository

class GetToDoUseCase(
    private val repository: AppRepository
): UseCase.NoParam<List<ToDo>>() {

    override suspend fun execute(): List<ToDo> {
        return repository.findAllToDos()
    }
}
