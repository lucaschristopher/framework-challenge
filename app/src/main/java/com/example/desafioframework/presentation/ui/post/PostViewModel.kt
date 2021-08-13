package com.example.desafioframework.presentation.ui.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.desafioframework.core.AppState
import com.example.desafioframework.domain.usecase.GetPostsUseCase
import kotlinx.coroutines.launch

class PostViewModel(
    private val getPostsUseCase: GetPostsUseCase
) : ViewModel() {

    private val _postLiveData = MutableLiveData<AppState<*>>()
    val postLiveData: LiveData<AppState<*>> = _postLiveData

    fun init() {
        findAllPosts()
    }

    private fun findAllPosts() {
        viewModelScope.launch {
            val response = getPostsUseCase.execute()
            when (response) {
                is AppState.Error -> _postLiveData.postValue(AppState.Error(response.exception))
                is AppState.Success -> _postLiveData.postValue(AppState.Success(response.successData))
            }
        }
    }
}