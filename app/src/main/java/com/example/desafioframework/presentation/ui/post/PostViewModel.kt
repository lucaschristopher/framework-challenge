package com.example.desafioframework.presentation.ui.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.desafioframework.domain.usecase.GetPostsUseCase

class PostViewModel(
    private val getPostsUseCase: GetPostsUseCase
) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text
}