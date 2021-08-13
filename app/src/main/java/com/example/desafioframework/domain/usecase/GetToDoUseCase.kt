package com.example.desafioframework.domain.usecase

import com.example.desafioframework.core.AppState
import com.example.desafioframework.core.UseCase
import com.example.desafioframework.data.model.ToDo
import com.example.desafioframework.data.repository.ToDoRepository

class GetToDoUseCase(
    private val repository: ToDoRepository
) : UseCase.NoParam<AppState<List<ToDo>>>() {

    override suspend fun execute(): AppState<List<ToDo>> {
        return repository.findAllToDos()
    }
}
