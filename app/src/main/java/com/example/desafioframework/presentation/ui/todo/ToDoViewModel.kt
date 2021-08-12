package com.example.desafioframework.presentation.ui.todo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.desafioframework.core.AppState
import com.example.desafioframework.data.model.ToDo
import com.example.desafioframework.domain.usecase.GetToDoUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class ToDoViewModel(
    private val getToDoUseCase: GetToDoUseCase
) : ViewModel() {

    private val _todoLiveData = MutableLiveData<AppState>()
    val todoLiveData: LiveData<AppState> = _todoLiveData

    fun init() {
        findAllTodos()
    }

    private fun findAllTodos() {
        viewModelScope.launch {
            val response = getToDoUseCase.execute()
            _todoLiveData.postValue(AppState.Success(response))
        }
    }
}