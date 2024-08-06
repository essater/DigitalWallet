package com.example.digitalwallet.data.repository

import com.example.digitalwallet.generateRandomIBAN
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class AuthRepository(private val firestoreRepository: FirestoreRepository) {

    private val auth = FirebaseAuth.getInstance()

    fun login(email: String, password: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->
                result.user?.let {
                    onSuccess()
                } ?: onFailure(Exception("User is null"))
            }
            .addOnFailureListener(onFailure)
    }

    fun signUp(email: String, password: String, name: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->
                val userId = result.user?.uid ?: return@addOnSuccessListener
                val iban = generateRandomIBAN()
                firestoreRepository.addUserToExistingCollection(
                    userId, name, email, password, iban,
                    onSuccess = {
                        firestoreRepository.addAccountData(
                            userId,
                            balance = "0.0",
                            spent = "0.0",
                            earned = "0.0",
                            iban = iban,
                            onSuccess = onSuccess,
                            onFailure = onFailure
                        )
                    },
                    onFailure = onFailure
                )
            }
            .addOnFailureListener(onFailure)
    }

    fun getCurrentUser(): FirebaseUser? = auth.currentUser
}