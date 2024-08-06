package com.example.digitalwallet

import java.util.Random

fun generateRandomIBAN(): String {
    val random = Random()
    val countryCode = "TR"
    val bankCode = String.format("%04d", random.nextInt(10000))
    val branchCode = String.format("%04d", random.nextInt(10000))
    val accountNumber1 = String.format("%04d", random.nextInt(10000))
    val accountNumber2 = String.format("%04d", random.nextInt(10000))
    val accountNumber3 = String.format("%04d", random.nextInt(10000))
    val checksum = String.format("%02d", random.nextInt(100))

    return "$countryCode$checksum$bankCode$branchCode$accountNumber1$accountNumber2$accountNumber3$checksum"
}
fun formatIBAN(iban: String): String {
    return iban.chunked(4).joinToString(" ")
}