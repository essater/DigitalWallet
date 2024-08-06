package com.example.digitalwallet.viewModel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.digitalwallet.data.repository.AuthRepository
import com.example.digitalwallet.data.repository.FirestoreRepository
import com.example.digitalwallet.viewModel.SignUpViewModel

class SignUpViewModelFactory(
    private val authRepository: AuthRepository,
    private val firestoreRepository: FirestoreRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SignUpViewModel(authRepository, firestoreRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}