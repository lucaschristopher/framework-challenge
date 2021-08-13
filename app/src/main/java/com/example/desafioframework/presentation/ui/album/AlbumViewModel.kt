package com.example.desafioframework.presentation.ui.album

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.desafioframework.core.AppState
import com.example.desafioframework.domain.usecase.GetAlbumsUseCase
import kotlinx.coroutines.launch

class AlbumViewModel(
    private val getAlbumsUseCase: GetAlbumsUseCase
) : ViewModel() {

    private val _albumLiveData = MutableLiveData<AppState<*>>()
    val albumLiveData: LiveData<AppState<*>> = _albumLiveData

    fun init() {
        findAllPosts()
    }

    private fun findAllPosts() {
        viewModelScope.launch {
            val response = getAlbumsUseCase.execute()
            when (response) {
                is AppState.Error -> _albumLiveData.postValue(AppState.Error(response.exception))
                is AppState.Success -> _albumLiveData.postValue(AppState.Success(response.successData))
            }
        }
    }
}