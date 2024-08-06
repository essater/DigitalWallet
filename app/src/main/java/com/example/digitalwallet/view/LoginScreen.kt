package com.example.digitalwallet.ui.viewmodel

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults.outlinedTextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.digitalwallet.R
import com.example.digitalwallet.ui.customUncheckedColor
import com.example.digitalwallet.ui.myCustomColor
import com.example.digitalwallet.utils.rememberKeyboardVisibility
import com.example.digitalwallet.viewModel.LoginViewModel
import com.google.firebase.auth.FirebaseAuth


@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController, loginViewModel: LoginViewModel = viewModel(), onLoginSuccess: () -> Unit) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val errorState by loginViewModel.errorState.collectAsState()
    val isKeyboardVisible = rememberKeyboardVisibility()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .padding(bottom = if (isKeyboardVisible) 100.dp else 0.dp)
            .systemBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        Text(
            text = "Welcome Back!",
            style = TextStyle(color = Color.Black, fontSize = 35.sp),
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Sign in to continue",
            style = TextStyle(color = Color.Black.copy(alpha = 0.6f), fontSize = 15.sp),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(65.dp))
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                loginViewModel.setEmail(it)
            },
            label = { Text("Email", color = Color.Black) },
            shape = RoundedCornerShape(16.dp),
            colors = outlinedTextFieldColors(
                focusedTextColor = Color.Black,
                focusedBorderColor = customUncheckedColor,
                unfocusedBorderColor = customUncheckedColor,
                cursorColor = Color.Blue

            ),

            modifier = Modifier
                .size(width = 340.dp, height = 65.dp)

        )

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                loginViewModel.setPassword(it)
            },
            label = { Text("Password", color = Color.Black) },
            shape = RoundedCornerShape(16.dp),
            colors = outlinedTextFieldColors(
                focusedTextColor = Color.Black,
                focusedBorderColor = myCustomColor,
                unfocusedBorderColor = customUncheckedColor,
                cursorColor = Color.Blue
            ),
            modifier = Modifier
                .size(width = 340.dp, height = 65.dp),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Default.Visibility
                else Icons.Default.VisibilityOff

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = image,
                        modifier = Modifier.graphicsLayer(alpha = 0.5f),
                        contentDescription = if (passwordVisible) "Hide password" else "Show password"
                    )
                }
            }
        )

        TextButton(onClick = {  },
            modifier = Modifier.offset(x = 110.dp)) {
            Text(
                "Forgot Password?",
                color = Color.Black,
                style = TextStyle(color = Color.Black, fontSize = 12.sp),
            )
        }

        Spacer(modifier = Modifier.height(40.dp))
        Button(
            onClick = {
                loginViewModel.onLoginClick(FirebaseAuth.getInstance()) {
                    navController.navigate("main")
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.my_custom_color)),
            modifier = Modifier
                .size(width = 340.dp, height = 60.dp)
                .background(color = myCustomColor, shape = RoundedCornerShape(15.dp))
                .clip(RoundedCornerShape(16.dp))
        ) {
            Text(
                "Sign in My Account",
                color = Color.White,
                style = TextStyle(color = Color.White, fontSize = 17.sp),
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }

        errorState?.let { error ->
            Text(
                text = error.message,
                color = Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        TextButton(onClick = { navController.navigate("signup") }) {
            Text(
                "Don't have an account? -Sign up",
                color = Color.Black,
                style = TextStyle(color = Color.Black, fontSize = 15.sp),
            )
        }
    }
}