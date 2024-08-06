package com.example.digitalwallet.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalwallet.data.repository.AuthRepository
import com.example.digitalwallet.data.repository.FirestoreRepository
import com.example.digitalwallet.generateRandomIBAN
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val authRepository: AuthRepository,
    private val firestoreRepository: FirestoreRepository
) : ViewModel() {

    private val _email = mutableStateOf("")
    val email: State<String> = _email

    private val _password = mutableStateOf("")
    val password: State<String> = _password

    private val _confirmPassword = mutableStateOf("")
    val confirmPassword: State<String> = _confirmPassword

    private val _fullName = mutableStateOf("")
    val fullName: State<String> = _fullName

    private val _isChecked = mutableStateOf(false)
    val isChecked: State<Boolean> = _isChecked

    private val _passwordVisible = mutableStateOf(false)
    val passwordVisible: State<Boolean> = _passwordVisible

    private val _passwordAgainVisible = mutableStateOf(false)
    val passwordAgainVisible: State<Boolean> = _passwordAgainVisible

    private val _errorState = MutableStateFlow<SignUpError?>(null)
    val errorState: StateFlow<SignUpError?> = _errorState.asStateFlow()

    fun onSignUpClick(onSignUpSuccess: () -> Unit) {
        val validationErrors = validatePassword(password.value, confirmPassword.value)
        if (validationErrors.isEmpty()) {
            viewModelScope.launch {
                try {
                    authRepository.signUp(email.value, password.value, fullName.value,
                        onSuccess = {
                            val userId = authRepository.getCurrentUser()?.uid ?: return@signUp
                            val iban = generateRandomIBAN()
                            firestoreRepository.addUserToExistingCollection(
                                userId,
                                fullName.value,
                                email.value,
                                password.value,
                                iban,
                                onSuccess = {
                                    firestoreRepository.addAccountData(
                                        userId,
                                        balance = "0.0",
                                        spent = "0.0",
                                        earned = "0.0",
                                        iban = iban,
                                        onSuccess = {
                                            onSignUpSuccess()
                                        },
                                        onFailure = { e ->
                                            _errorState.value = SignUpError.SignUpFailed(e.message ?: "Sign up failed.")
                                        }
                                    )
                                },
                                onFailure = { e ->
                                    _errorState.value = SignUpError.SignUpFailed(e.message ?: "Sign up failed.")
                                }
                            )
                        },
                        onFailure = { e ->
                            _errorState.value = SignUpError.SignUpFailed(e.message ?: "Sign up failed.")
                        }
                    )
                } catch (e: Exception) {
                    _errorState.value = SignUpError.SignUpFailed("Sign up failed.")
                }
            }
        } else {
            _errorState.value = SignUpError.ValidationErrors(validationErrors)
        }
    }

    fun setEmail(email: String) {
        _email.value = email
    }

    fun setPassword(password: String) {
        _password.value = password
    }

    fun setConfirmPassword(confirmPassword: String) {
        _confirmPassword.value = confirmPassword
    }

    fun setFullName(fullName: String) {
        _fullName.value = fullName
    }

    fun setIsChecked(isChecked: Boolean) {
        _isChecked.value = isChecked
    }

    fun setPasswordVisible(passwordVisible: Boolean) {
        _passwordVisible.value = passwordVisible
    }

    fun setPasswordAgainVisible(passwordAgainVisible: Boolean) {
        _passwordAgainVisible.value = passwordAgainVisible
    }

    private fun validatePassword(password: String, confirmPassword: String): List<String> {
        val errors = mutableListOf<String>()
        if (password.length < 8) errors.add("Password must be at least 8 characters")
        if (password != confirmPassword) errors.add("Passwords do not match")
        else {
            if (!password.any { it.isLowerCase() }) errors.add("Password must contain at least one lowercase letter")
            if (!password.any { it.isUpperCase() }) errors.add("Password must contain at least one uppercase letter")
            if (!password.any { it.isDigit() }) errors.add("Password must contain at least one digit")
            if (!password.any { it in "!@#\$%^&*()_+-=[]|,./?><" }) errors.add("Password must contain at least one special character")
        }
        return errors
    }
}

sealed class SignUpError(val message: String) {
    class SignUpFailed(message: String) : SignUpError(message)
    class VerificationFailed(message: String) : SignUpError(message)
    class ValidationErrors(val errors: List<String>) : SignUpError("Validation errors occurred")
}