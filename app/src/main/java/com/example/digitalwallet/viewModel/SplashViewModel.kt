package com.example.digitalwallet.viewModel



import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {

    private val _splashScreenShown = MutableLiveData(false)
    val splashScreenShown: LiveData<Boolean> = _splashScreenShown

    init {
        viewModelScope.launch {
            delay(3000)
            _splashScreenShown.value = true
        }
    }
}