package com.example.medics

import com.example.medics.ui.theme.Screen.SignUpScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.medics.ui.screens.HomeScreen
import com.example.medics.ui.theme.AppDestinations
import com.example.medics.ui.theme.Screen.LoginOrSignUpScreen
import com.example.medics.ui.theme.Screen.LoginScreen
import com.example.medics.ui.theme.Screen.OnboardingScreen
import com.example.medics.ui.theme.Screen.SplashScreen
import com.example.medics.ui.theme.Screen.TopDoctorsScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var showSplashScreen by remember { mutableStateOf(true) }
            val navigateToApp = { showSplashScreen = false }
            LaunchedEffect(Unit) {
                if (showSplashScreen) { navigateToApp() }
            }
            if (showSplashScreen) {
                SplashScreen(onNextScreen = navigateToApp)
            } else {
                MedicsApp()
            }
        }
    }
}

@Composable
fun MedicsApp() {
    val navController = rememberNavController()
    var loggedInUserName by remember { mutableStateOf<String?>(null) }
    var loggedInUserEmail by remember { mutableStateOf<String?>(null) }

    NavHost(
        navController = navController,
        startDestination = AppDestinations.SPLASH_ROUTE
    ) {
        composable(AppDestinations.SPLASH_ROUTE) {
            SplashScreen(onNextScreen = {
                navController.navigate(AppDestinations.ONBOARDING_ROOT_ROUTE) {
                    popUpTo(AppDestinations.SPLASH_ROUTE) { inclusive = true }
                }
            })
        }

        composable(AppDestinations.ONBOARDING_ROOT_ROUTE) {
            val doctorImages = listOf(
                R.drawable.dokter_1_onboard,
                R.drawable.dokter_2_onboard,
                R.drawable.dokter_3_onboard
            )
            val doctorCaptions = listOf(
                "Consult only with a doctor you trust",
                "Find a lot of specialist doctors in one place",
                "Get connect our Online Consultation"
            )
            var currentOnboardingPage by remember { mutableIntStateOf(0) }
            val totalDoctorPages = doctorImages.size

            OnboardingScreen(
                imageRes = doctorImages[currentOnboardingPage],
                caption = doctorCaptions[currentOnboardingPage],
                onNextClicked = {
                    if (currentOnboardingPage < totalDoctorPages - 1) {
                        currentOnboardingPage++
                    } else {
                        navController.navigate(AppDestinations.LOGIN_SIGNUP_ROUTE) {
                            popUpTo(AppDestinations.ONBOARDING_ROOT_ROUTE) { inclusive = true }
                        }
                    }
                },
                onSkipClicked = {
                    navController.navigate(AppDestinations.LOGIN_SIGNUP_ROUTE) {
                        popUpTo(AppDestinations.ONBOARDING_ROOT_ROUTE) { inclusive = true }
                    }
                }
            )
        }

        composable(AppDestinations.LOGIN_SIGNUP_ROUTE) {
            LoginOrSignUpScreen(
                onLoginClicked = { navController.navigate(AppDestinations.LOGIN_ROUTE) },
                onSignUpClicked = { navController.navigate(AppDestinations.SIGN_UP_ROUTE) }
            )
        }

        composable(AppDestinations.LOGIN_ROUTE) {
            LoginScreen(
                onNavigateBack = { navController.popBackStack() },
                onSignInSuccess = {
                    loggedInUserEmail = "user@example.com"
                    navController.navigate(AppDestinations.HOME_ROOT_ROUTE) {
                        popUpTo(AppDestinations.LOGIN_SIGNUP_ROUTE) { inclusive = true }
                    }
                    println("Login berhasil!")
                },
                onNavigateToSignUp = { navController.navigate(AppDestinations.SIGN_UP_ROUTE) }
            )
        }

        composable(AppDestinations.SIGN_UP_ROUTE) {
            SignUpScreen(
                onNavigateBack = { navController.popBackStack() },
                onSignUpSuccess = { name, email ->
                    loggedInUserName = name
                    loggedInUserEmail = email
                    navController.navigate(AppDestinations.HOME_ROOT_ROUTE) {
                        popUpTo(AppDestinations.LOGIN_SIGNUP_ROUTE) { inclusive = true }
                    }
                    println("Sign up berhasil! Nama: $name, Email: $email")
                }
            )
        }

        composable(AppDestinations.HOME_ROOT_ROUTE) {
            HomeScreen(
                onLogoutClicked = {
                    loggedInUserName = null
                    loggedInUserEmail = null
                    navController.navigate(AppDestinations.LOGIN_SIGNUP_ROUTE) {
                        popUpTo(AppDestinations.HOME_ROOT_ROUTE) { inclusive = true }
                    }
                    println("Logout berhasil!")
                },
                onSearchQueryChanged = { query -> println("Search: $query") },
                onNotificationClicked = { println("Notification clicked") },
                onDoctorCardClicked = { doctorId -> println("Doctor card: $doctorId") },
                onSeeAllDoctorsClicked = { navController.navigate(AppDestinations.TOP_DOCTORS_SCREEN_ROUTE) },
                onBottomNavItemClicked = { route ->
                },
                onNavigateToChatScreen = { conversationId ->
                    println("Navigate to chat screen with ID: $conversationId from MedicsApp")
                },
                onArticleClicked = { articleId ->
                    println("Article clicked: $articleId")
                }
            )
        }
        composable(AppDestinations.TOP_DOCTORS_SCREEN_ROUTE) {
            TopDoctorsScreen(
                onNavigateBack = { navController.popBackStack() },
                onDoctorCardClicked = { doctorId ->
                    println("Doctor card clicked on Top Doctors Screen: $doctorId")
                }
            )
        }
    }
}

@Preview(showBackground = true, name = "App Flow Preview")
@Composable
fun DefaultPreview() {
    MedicsApp()
}