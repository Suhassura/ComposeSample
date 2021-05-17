package com.compose.panthercompose.pokemon.presentation.ui.splash_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class SplashViewModel @Inject constructor(
) : ViewModel() {

    val isCompleted = mutableStateOf(false)

    init {
        viewModelScope.launch {
            delay(2000L)
            isCompleted.value = true
        }
    }
}