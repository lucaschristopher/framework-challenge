package com.example.desafioframework.core

import java.lang.Exception

sealed class AppState<out T> {
    data class Success<out T>(val successData: T) : AppState<T>()
    data class Error(val exception: Exception, val message: String = exception.message!!) : AppState<Nothing>()
}