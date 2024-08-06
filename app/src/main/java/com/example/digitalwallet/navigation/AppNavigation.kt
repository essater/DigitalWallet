package com.example.digitalwallet.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.digitalwallet.data.repository.AuthRepository
import com.example.digitalwallet.data.repository.FirestoreRepository
import com.example.digitalwallet.ui.viewmodel.LoginScreen
import com.example.digitalwallet.view.MainScreen
import com.example.digitalwallet.view.SignUpScreen
import com.example.digitalwallet.view.SplashScreen
import com.example.digitalwallet.viewModel.LoginViewModel
import com.example.digitalwallet.viewModel.MainViewModel
import com.example.digitalwallet.viewModel.SignUpViewModel
import com.example.digitalwallet.viewModel.SplashViewModel
import com.example.digitalwallet.viewModel.factory.LoginViewModelFactory
import com.example.digitalwallet.viewModel.factory.MainViewModelFactory
import com.example.digitalwallet.viewModel.factory.SignUpViewModelFactory
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val authRepository = AuthRepository(firestoreRepository = FirestoreRepository())
    val firestoreRepository = FirestoreRepository()

    NavHost(navController = navController, startDestination = "splash_screen") {
        composable("splash_screen") {
            val splashViewModel: SplashViewModel = viewModel()
            SplashScreen(navController = navController, splashViewModel = splashViewModel)
        }
        composable("login") {
            val loginViewModel: LoginViewModel = viewModel(factory = LoginViewModelFactory(authRepository, firestoreRepository))
            LoginScreen(navController, loginViewModel) {
                navController.navigate("main")
            }
        }
        composable("signup") {
            val signUpViewModel: SignUpViewModel = viewModel(factory = SignUpViewModelFactory(authRepository,firestoreRepository))
            SignUpScreen(navController, signUpViewModel) {
                navController.navigate("login")
            }
        }
        composable("main") {
            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
            val mainViewModel: MainViewModel = viewModel(factory = MainViewModelFactory(firestoreRepository))
            MainScreen(navController, mainViewModel, userId)
        }
    }
}

/*@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "splash_screen") {
        composable("splash_screen") {
            val splashViewModel: SplashViewModel = viewModel()
            SplashScreen(navController = navController, splashViewModel = splashViewModel)
        }
        composable("login") {
            val loginViewModel: LoginViewModel = viewModel(factory = LoginViewModelFactory(
                AuthRepository(), FirestoreRepository()
            ))
            LoginScreen(navController, loginViewModel) {
                navController.navigate("main")
            }
        }
        composable("signup") {
            val signUpViewModel: SignUpViewModel = viewModel(factory = SignUpViewModelFactory(
                AuthRepository()
            ))
            SignUpScreen(navController, signUpViewModel) {
                navController.navigate("login")
            }
        }
        composable("main") {
            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
            val mainViewModel: MainViewModel = viewModel(factory = MainViewModelFactory(
                FirestoreRepository()
            ))
            MainScreen(navController, mainViewModel, userId)
        }
    }
}*/