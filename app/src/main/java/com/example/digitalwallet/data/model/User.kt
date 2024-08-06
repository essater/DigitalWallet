package com.example.digitalwallet.data.model

data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val iban: String = "",
    val balance: Double = 0.0,
    val earned: Double = 0.0,
    val spent: Double = 0.0
)