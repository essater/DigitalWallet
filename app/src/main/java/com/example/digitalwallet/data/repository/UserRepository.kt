package com.example.digitalwallet.data.repository

import com.google.firebase.firestore.FirebaseFirestore

class UserRepository {

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun saveUserIBAN(userId: String, iban: String, onComplete: (Boolean) -> Unit) {
        val userRef = db.collection("users").document(userId)
        userRef.update("iban", iban)
            .addOnCompleteListener { task ->
                onComplete(task.isSuccessful)
            }
    }

    fun getUserIBAN(userId: String, onResult: (String?) -> Unit) {
        val userRef = db.collection("users").document(userId)
        userRef.get()
            .addOnSuccessListener { document ->
                val iban = document.getString("iban")
                onResult(iban)
            }
            .addOnFailureListener {
                onResult(null)
            }
    }
}