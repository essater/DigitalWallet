package com.example.digitalwallet.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.digitalwallet.viewModel.SignUpError
import com.example.digitalwallet.viewModel.SignUpViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(navController: NavController, signUpViewModel: SignUpViewModel = viewModel(), onSignupSuccess: () -> Unit = {}) {

    val email by signUpViewModel.email
    val password by signUpViewModel.password
    val confirmPassword by signUpViewModel.confirmPassword
    val fullName by signUpViewModel.fullName
    val isChecked by signUpViewModel.isChecked
    val errors by signUpViewModel.errorState.collectAsState()
    val isKeyboardVisible = rememberKeyboardVisibility()

    val passwordVisible by signUpViewModel.passwordVisible
    val passwordAgainVisible by signUpViewModel.passwordAgainVisible

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .padding(bottom = if (isKeyboardVisible) 100.dp else 0.dp)
            .systemBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Welcome!",
            style = TextStyle(color = Color.Black, fontSize = 35.sp),
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Please provide following \ndetails for your new account",
            style = TextStyle(color = Color.Black.copy(alpha = 0.6f), fontSize = 15.sp),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(65.dp))

        OutlinedTextField(
            value = fullName,
            onValueChange = { signUpViewModel.setFullName(it) },
            label = { Text("Full Name", color = Color.Black) },
            shape = RoundedCornerShape(16.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedTextColor = Color.Black,
                focusedBorderColor = myCustomColor,
                unfocusedBorderColor = customUncheckedColor,
                cursorColor = Color.Blue
            ),
            modifier = Modifier
                .size(width = 340.dp, height = 65.dp)
                .background(Color.Transparent)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { signUpViewModel.setEmail(it) },
            label = { Text("Email", color = Color.Black) },
            shape = RoundedCornerShape(16.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedTextColor = Color.Black,
                focusedBorderColor = myCustomColor,
                unfocusedBorderColor = customUncheckedColor,
                cursorColor = Color.Blue
            ),
            modifier = Modifier
                .size(width = 340.dp, height = 65.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { signUpViewModel.setPassword(it) },
            label = { Text("Password", color = Color.Black) },
            shape = RoundedCornerShape(16.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
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

                IconButton(onClick = { signUpViewModel.setPasswordVisible(!passwordVisible) }) {
                    Icon(
                        imageVector = image,
                        modifier = Modifier.graphicsLayer(alpha = 0.5f),
                        contentDescription = if (passwordVisible) "Hide password" else "Show password"
                    )
                }
            }
        )

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { signUpViewModel.setConfirmPassword(it) },
            label = { Text("Confirm Password", color = Color.Black) },
            shape = RoundedCornerShape(16.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedTextColor = Color.Black,
                focusedBorderColor = myCustomColor,
                unfocusedBorderColor = customUncheckedColor,
                cursorColor = Color.Blue
            ),
            modifier = Modifier
                .size(width = 340.dp, height = 65.dp),
            visualTransformation = if (passwordAgainVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (passwordAgainVisible)
                    Icons.Default.Visibility
                else Icons.Default.VisibilityOff

                IconButton(onClick = { signUpViewModel.setPasswordAgainVisible(!passwordAgainVisible) }) {
                    Icon(
                        imageVector = image,
                        modifier = Modifier.graphicsLayer(alpha = 0.5f),
                        contentDescription = if (passwordAgainVisible) "Hide password" else "Show password"
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = { signUpViewModel.setIsChecked(it) },
                colors = CheckboxDefaults.colors(
                    checkedColor = myCustomColor,
                    uncheckedColor = customUncheckedColor
                ),
            )
            Spacer(modifier = Modifier
                .width(0.5.dp)
                .offset(x = 5.dp))
            Text(
                text = "By creating your account you have to agree \nwith our Terms and Conditions",
                style = TextStyle(color = Color.Black.copy(alpha = 0.6f), fontSize = 13.sp),
                modifier = Modifier.offset(x = 6.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                signUpViewModel.onSignUpClick {
                    navController.navigate("login")
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.my_custom_color)),
            modifier = Modifier
                .size(width = 340.dp, height = 65.dp)
                .background(color = myCustomColor, shape = RoundedCornerShape(15.dp))
                .clip(RoundedCornerShape(16.dp))
        ) {
            Text(
                "Sign up My Account",
                color = Color.White,
                style = TextStyle(color = Color.White, fontSize = 17.sp),
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }

        errors?.let { error ->
            when (error) {
                is SignUpError.ValidationErrors -> {
                    error.errors.forEach { validationError ->
                        Text(
                            text = validationError,
                            color = Color.Red,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
                else -> {
                    Text(
                        text = error.message,
                        color = Color.Red,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        TextButton(onClick = { navController.navigate("login") }) {
            Text(
                "Already have an account? - Login",
                color = Color.Black,
                style = TextStyle(color = Color.Black, fontSize = 15.sp),
            )
        }
    }
}