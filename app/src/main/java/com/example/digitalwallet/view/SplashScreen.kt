package com.example.digitalwallet.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.digitalwallet.ui.myCustomColor
import com.example.digitalwallet.viewModel.SplashViewModel

@Composable
fun SplashScreen(navController: NavController, splashViewModel: SplashViewModel = viewModel()) {

    val splashScreenShown by splashViewModel.splashScreenShown.observeAsState(initial = false)

    LaunchedEffect(splashScreenShown) {
        if (splashScreenShown) {
            navController.navigate("login") {
                popUpTo("splash_screen") { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = myCustomColor),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Bank EC",
                fontSize = 70.sp,
                fontWeight = FontWeight.Normal,
                color = Color.White,
                fontStyle = FontStyle.Normal
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Bank, Finance Kit",
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal,
                color = Color.White,
                fontStyle = FontStyle.Normal
            )
        }
}}

@Preview
@Composable
fun PreSplash(){
    SplashScreen(navController = rememberNavController(), splashViewModel = viewModel())
}