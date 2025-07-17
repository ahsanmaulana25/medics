package com.example.medics

import android.content.Intent
import android.net.Uri
import com.example.medics.ui.theme.Screen.SignUpScreen
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.medics.ui.screens.HomeScreen
import com.example.medics.ui.theme.AppDestinations
import com.example.medics.ui.theme.Screen.LoginOrSignUpScreen
import com.example.medics.ui.theme.Screen.LoginScreen
import com.example.medics.ui.theme.Screen.OnboardingScreen
import com.example.medics.ui.theme.Screen.PharmacyScreen
import com.example.medics.ui.theme.Screen.SplashScreen
import com.example.medics.ui.theme.Screen.TopDoctorsScreen
import com.example.medics.ui.theme.Screen.DoctorDetailScreen

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
    val context = LocalContext.current

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
            var currentOnboardingPage by remember { mutableStateOf(0) }
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
                onDoctorCardClicked = { doctorId ->
                    navController.navigate("${AppDestinations.DOCTOR_DETAIL_ROUTE}/$doctorId")
                },
                onSeeAllDoctorsClicked = { navController.navigate(AppDestinations.TOP_DOCTORS_SCREEN_ROUTE) },
                onBottomNavItemClicked = { route ->
                },
                onNavigateToChatScreen = { conversationId ->
                    println("Navigate to chat screen with ID: $conversationId from MedicsApp")
                },
                onArticleClicked = { articleId ->
                    println("Article clicked: $articleId")
                },
                onPharmacyShortcutClicked = { navController.navigate(AppDestinations.PHARMACY_SCREEN_ROUTE) },
                onHospitalShortcutClicked = {
                    val gmmIntentUri = Uri.parse("geo:0,0?q=hospitals")
                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                    mapIntent.setPackage("com.google.android.apps.maps")

                    try {
                        context.startActivity(mapIntent)
                    } catch (e: Exception) {
                        Toast.makeText(context, "Tidak dapat membuka aplikasi peta. Pastikan Google Maps terinstal.", Toast.LENGTH_LONG).show()
                        println("Error: Tidak dapat membuka aplikasi peta. Pastikan Google Maps terinstal.")
                    }
                },
                onAmbulanceShortcutClicked = {
                    val phoneNumber = "6285142947225"
                    val whatsappUrl = "https://wa.me/$phoneNumber"
                    val whatsappIntent = Intent(Intent.ACTION_VIEW)
                    whatsappIntent.data = Uri.parse(whatsappUrl)
                    whatsappIntent.setPackage("com.whatsapp")

                    try {
                        context.startActivity(whatsappIntent)
                        Toast.makeText(context, "Membuka WhatsApp...", Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        Toast.makeText(context, "WhatsApp tidak terinstal atau tidak dapat dibuka. Mencoba panggilan telepon biasa.", Toast.LENGTH_LONG).show()
                        val dialIntent = Intent(Intent.ACTION_DIAL)
                        dialIntent.data = Uri.parse("tel:$phoneNumber")
                        try {
                            context.startActivity(dialIntent)
                        } catch (e2: Exception) {
                            Toast.makeText(context, "Tidak dapat melakukan panggilan. Pastikan aplikasi telepon berfungsi.", Toast.LENGTH_LONG).show()
                            println("Error: Tidak dapat melakukan panggilan. ${e2.message}")
                        }
                    }
                },
                onSeeAllArticlesClicked = {
                    val websiteUrl = "https://www.medicalnewstoday.com/"
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(websiteUrl))
                    try {
                        context.startActivity(browserIntent)
                        Toast.makeText(context, "Membuka Medical News Today...", Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        Toast.makeText(context, "Tidak dapat membuka browser. Pastikan ada browser terinstal.", Toast.LENGTH_LONG).show()
                        println("Error: Tidak dapat membuka browser. ${e.message}")
                    }
                },
                onLearnMoreClicked = {
                    val learnMoreUrl = "file:///Users/macbook/Documents/website/MedicsWebsite/index.html"
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(learnMoreUrl))
                    try {
                        context.startActivity(browserIntent)
                        Toast.makeText(context, "Membuka halaman informasi lokal...", Toast.LENGTH_LONG).show()
                    } catch (e: Exception) {
                        Toast.makeText(context, "Tidak dapat membuka file lokal. Pastikan jalur file benar dan dapat diakses.", Toast.LENGTH_LONG).show()
                        println("Error: Tidak dapat membuka file lokal. ${e.message}")
                    }
                }
            )
        }

        composable(AppDestinations.TOP_DOCTORS_SCREEN_ROUTE) {
            TopDoctorsScreen(
                onNavigateBack = { navController.popBackStack() },
                onDoctorCardClicked = { doctorId ->
                    navController.navigate("${AppDestinations.DOCTOR_DETAIL_ROUTE}/$doctorId")
                }
            )
        }

        composable(AppDestinations.PHARMACY_SCREEN_ROUTE) {
            PharmacyScreen(
                onNavigateBack = { navController.popBackStack() },
                onUploadPrescriptionClicked = { println("Upload Resep dari Pharmacy Screen") },
                onAddToCartClicked = { productId -> println("Produk $productId ditambahkan ke keranjang dari Pharmacy Screen") },
                onSeeAllProductsClicked = { println("Lihat Semua Produk dari Pharmacy Screen") }
            )
        }

        composable(
            route = "${AppDestinations.DOCTOR_DETAIL_ROUTE}/{doctorId}",
            arguments = listOf(navArgument("doctorId") { type = NavType.StringType })
        ) { backStackEntry ->
            val doctorId = backStackEntry.arguments?.getString("doctorId")
            DoctorDetailScreen(
                doctorId = doctorId,
                onNavigateBack = { navController.popBackStack() },
                onBookAppointmentClicked = { docId, selectedDate, selectedTime ->
                    println("Booking appointment for doctor $docId on $selectedDate at $selectedTime")
                    Toast.makeText(context, "Janji temu dengan $docId pada $selectedDate pukul $selectedTime berhasil dipesan!", Toast.LENGTH_LONG).show()
                },
                onChatClicked = { docId ->
                    println("Chat with doctor $docId clicked from detail screen")
                }
            )
        }
    }
}

@Preview(showBackground = true, name = "App Flow Preview" )
@Composable
fun DefaultPreview() {
    MedicsApp()
}