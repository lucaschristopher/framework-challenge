package com.example.desafioframework.core

sealed class AppState {
    object Loading : AppState()
    data class Success(val list: List<*>) : AppState()
    data class Error(val error: Throwable) : AppState()
}