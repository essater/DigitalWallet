package com.example.digitalwallet.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalwallet.data.repository.AuthRepository
import com.example.digitalwallet.data.repository.FirestoreRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val authRepository: AuthRepository,
                     private val firestoreRepository: FirestoreRepository
) : ViewModel() {

    private val _email = mutableStateOf("")
    val email: State<String> = _email

    private val _password = mutableStateOf("")
    val password: State<String> = _password

    private val _errorState = MutableStateFlow<LoginError?>(null)
    val errorState: StateFlow<LoginError?> = _errorState.asStateFlow()

    fun onLoginClick(auth: FirebaseAuth, onLoginSuccess: () -> Unit) {
        viewModelScope.launch {
            auth.signInWithEmailAndPassword(email.value, password.value)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        if (user != null) {
                            if (user.isEmailVerified) {
                                onLoginSuccess()
                            } else {
                                user.sendEmailVerification()
                                    .addOnCompleteListener { verificationTask ->
                                        if (verificationTask.isSuccessful) {
                                            _errorState.value = LoginError.VerificationNeeded(
                                                "Please verify your email address. A verification email has been sent."
                                            )
                                        } else {
                                            _errorState.value =
                                                LoginError.VerificationFailed("Failed to send verification email.")
                                        }
                                    }
                            }
                        } else {
                            _errorState.value = LoginError.AuthenticationFailed("User not found.")
                        }
                    } else {
                        _errorState.value =
                            LoginError.AuthenticationFailed("Authentication failed.")
                    }
                }
        }
    }

    fun setEmail(email: String) {
        _email.value = email
    }

    fun setPassword(password: String) {
        _password.value = password
    }
}

sealed class LoginError(val message: String) {
    class AuthenticationFailed(message: String) : LoginError(message)
    class VerificationNeeded(message: String) : LoginError(message)
    class VerificationFailed(message: String) : LoginError(message)
}