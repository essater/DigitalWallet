package com.example.digitalwallet.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

@Composable
fun rememberKeyboardVisibility(): Boolean {
    val view = LocalView.current
    var isKeyboardVisible by remember { mutableStateOf(false) }

    ViewCompat.setOnApplyWindowInsetsListener(view) { _, insets ->
        isKeyboardVisible = insets.isVisible(WindowInsetsCompat.Type.ime())
        insets
    }

    return isKeyboardVisible
}