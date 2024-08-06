package com.example.digitalwallet


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.digitalwallet.navigation.AppNavigation
import com.example.digitalwallet.ui.AppTheme


class DigitalWalletApplication : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                AppNavigation()
            }
        }
    }
}





