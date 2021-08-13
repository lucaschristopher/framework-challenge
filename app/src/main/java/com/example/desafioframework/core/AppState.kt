package com.example.desafioframework.core

import java.lang.Exception

// To help us handle the webservice and application cache return
sealed class AppState<out T> {

    data class Success<out T>(val successData: T) : AppState<T>()

    data class Error(val exception: Exception, val message: String = exception.message!!) : AppState<Nothing>()
}