package com.example.digitalwallet.viewModel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalwallet.data.repository.FirestoreRepository
import com.google.firebase.firestore.auth.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class MainViewModel(private val firestoreRepository: FirestoreRepository) : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user.asStateFlow()

    private val _transactions = MutableStateFlow<List<Map<String, Any>>?>(null)
    val transactions: StateFlow<List<Map<String, Any>>?> = _transactions.asStateFlow()

    fun loadUserData(userId: String) {
        viewModelScope.launch {
            try {
                val userData = firestoreRepository.getUserData(userId)
                _user.value = userData
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error loading user data", e)
            }
        }
    }

    fun loadUserTransactions(userId: String) {
        viewModelScope.launch {
            try {
                _transactions.value = firestoreRepository.getUserTransactions(userId)
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error loading user transactions", e)
            }
        }
    }
}