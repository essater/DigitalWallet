package com.example.digitalwallet.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalwallet.data.repository.AuthRepository
import kotlinx.coroutines.launch


class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _registrationState = MutableLiveData<Result<Unit>>()
    val registrationState: LiveData<Result<Unit>> = _registrationState

    fun login(email: String, password: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        viewModelScope.launch {
            authRepository.login(email, password, onSuccess, onFailure)
        }
    }

    fun signUp(email: String, password: String, name: String) {
        viewModelScope.launch {
            authRepository.signUp(email, password, name, {
                _registrationState.value = Result.success(Unit)
            }, { exception ->
                _registrationState.value = Result.failure(exception)
            })
        }
    }
}
/*
class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {

    fun login(email: String, password: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        viewModelScope.launch {
            authRepository.login(email, password, onSuccess, onFailure)
        }
    }

    fun signUp(email: String, password: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        viewModelScope.launch {
            authRepository.signUp(email, password, onSuccess, onFailure)
        }
    }
}*/
