package com.example.desafioframework.presentation.ui.todo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.desafioframework.core.AppState
import com.example.desafioframework.domain.usecase.GetToDoUseCase
import kotlinx.coroutines.launch

class ToDoViewModel(
    private val getToDoUseCase: GetToDoUseCase
) : ViewModel() {

    private val _todoLiveData = MutableLiveData<AppState<*>>()
    val todoLiveData: LiveData<AppState<*>> = _todoLiveData

    fun init() {
        findAllTodos()
    }

    private fun findAllTodos() {
        viewModelScope.launch {
            val response = getToDoUseCase.execute()
            when (response) {
                is AppState.Error -> _todoLiveData.postValue(AppState.Error(response.exception))
                is AppState.Success -> _todoLiveData.postValue(AppState.Success(response.successData))
            }
        }
    }
}