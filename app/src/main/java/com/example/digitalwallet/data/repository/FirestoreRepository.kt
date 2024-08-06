package com.example.digitalwallet.data.repository

import android.annotation.SuppressLint
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import kotlinx.coroutines.tasks.await


class FirestoreRepository {

    private val firestore = FirebaseFirestore.getInstance()

    fun addUserToExistingCollection(
        userId: String,
        name: String,
        email: String,
        password: String,
        iban: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val user = hashMapOf(
            "name" to name,
            "email" to email,
            "id" to userId,
            "iban" to iban,
            "balance" to 0.0,
            "earned" to 0.0,
            "spent" to 0.0,
            "password" to password
        )

        firestore.collection("users")
            .document(userId)
            .set(user)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { e ->
                onFailure(e)
            }
    }

    @SuppressLint("RestrictedApi")
    suspend fun getUserData(userId: String): User? {
        return try {
            val document = firestore.collection("users").document(userId).get().await()
            document.toObject(User::class.java)
        } catch (e: Exception) {
            Log.e("FirestoreRepository", "Error getting user data", e)
            null
        }
    }

    suspend fun updateUserBalance(uid: String, newBalance: Double) {
        firestore.collection("users").document(uid).update("balance", newBalance).await()
    }

    suspend fun getUserTransactions(uid: String): List<Map<String, Any>>? {
        val documents = firestore.collection("users").document(uid).collection("transactions").get().await()
        return if (!documents.isEmpty) {
            documents.documents.map { it.data!! }
        } else {
            null
        }
    }
}