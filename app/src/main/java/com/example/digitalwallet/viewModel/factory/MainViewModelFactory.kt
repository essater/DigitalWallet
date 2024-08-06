package com.example.digitalwallet.viewModel.factory


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.digitalwallet.data.repository.FirestoreRepository
import com.example.digitalwallet.viewModel.MainViewModel


class MainViewModelFactory(private val firestoreRepository: FirestoreRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(firestoreRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

