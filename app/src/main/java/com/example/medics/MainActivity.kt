package com.example.medics

import android.content.Intent
import android.net.Uri
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.medics.model.Doctor
import com.example.medics.ui.screens.HomeScreen
import com.example.medics.ui.theme.AppDestinations
import com.example.medics.ui.theme.Screen.BookingScreen
import com.example.medics.ui.theme.Screen.ChatScreen
import com.example.medics.ui.theme.Screen.DoctorDetailScreen
import com.example.medics.ui.theme.Screen.LoginOrSignUpScreen
import com.example.medics.ui.theme.Screen.LoginScreen
import com.example.medics.ui.theme.Screen.MessagesScreen
import com.example.medics.ui.theme.Screen.OnboardingScreen
import com.example.medics.ui.theme.Screen.PharmacyScreen
import com.example.medics.ui.theme.Screen.ProfileScreen
import com.example.medics.ui.theme.Screen.ScheduleScreen
import com.example.medics.ui.theme.Screen.SignUpScreen
import com.example.medics.ui.theme.Screen.SplashScreen
import com.example.medics.ui.theme.Screen.TopDoctorsScreen
import com.google.gson.Gson
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

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
                onNavigateToChatScreen = { conversationId, chatPartnerName, chatPartnerImageRes ->
                    val encodedChatPartnerName = URLEncoder.encode(chatPartnerName, StandardCharsets.UTF_8.toString())
                    navController.navigate(
                        "${AppDestinations.CHAT_SCREEN_ROUTE}/" +
                                "$conversationId/" +
                                "$encodedChatPartnerName/" +
                                "$chatPartnerImageRes"
                    )
                },
                onArticleClicked = { articleId ->
                    println("Article clicked: $articleId")
                },
                onPharmacyShortcutClicked = { navController.navigate(AppDestinations.PHARMACY_SCREEN_ROUTE) },
                onHospitalShortcutClicked = {
                    val gmmIntentUri = Uri.parse("geo:0,0?q=hospital")
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
                    val phoneNumber = "112"
                    val whatsappUri = Uri.parse("tel:$phoneNumber")
                    val callIntent = Intent(Intent.ACTION_DIAL, whatsappUri)

                    try {
                        context.startActivity(callIntent)
                    } catch (e: Exception) {
                        Toast.makeText(context, "Tidak dapat melakukan panggilan. Pastikan aplikasi telepon berfungsi.", Toast.LENGTH_LONG).show()
                        println("Error: Tidak dapat melakukan panggilan. Pastikan aplikasi telepon berfungsi. ${e.message}")
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
            val doctor = remember(doctorId) {
                when (doctorId) {
                    "doc1" -> Doctor("doc1", "Dr. Marcus Horizon", "Cardiologist", R.drawable.marcus, "City General Hospital", 4.8f, 230)
                    "doc2" -> Doctor("doc2", "Dr. Maria Elena", "Skin Specialist", R.drawable.maria, "Metro Hospital", 4.5f, 98)
                    "doc3" -> Doctor("doc3", "Dr. Stevi Jessi", "Heart Specialist", R.drawable.stefi, "Hope Clinic", 4.9f, 210)
                    else -> Doctor("dummy", "Dr. Dummy Dokter", "General Practitioner", R.drawable.dr_marcus_horizon, "Dummy Clinic", 4.0f, 50)
                }
            }
            DoctorDetailScreen(
                doctorId = doctorId,
                onNavigateBack = { navController.popBackStack() },
                onBookAppointmentClicked = { doc, selectedDate, selectedTime ->
                    val doctorJson = Gson().toJson(doc)
                    val encodedDoctorJson = URLEncoder.encode(doctorJson, StandardCharsets.UTF_8.toString())

                    navController.navigate("${AppDestinations.BOOKING_SCREEN_ROUTE}/$encodedDoctorJson/$selectedDate/$selectedTime")
                },
                onChatClicked = { docId ->
                    println("Chat with doctor $docId clicked from detail screen")
                }
            )
        }

        composable(
            route = "${AppDestinations.BOOKING_SCREEN_ROUTE}/{doctorJson}/{selectedDate}/{selectedTime}",
            arguments = listOf(
                navArgument("doctorJson") { type = NavType.StringType },
                navArgument("selectedDate") { type = NavType.StringType },
                navArgument("selectedTime") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val doctorJson = backStackEntry.arguments?.getString("doctorJson")
            val selectedDate = backStackEntry.arguments?.getString("selectedDate") ?: ""
            val selectedTime = backStackEntry.arguments?.getString("selectedTime") ?: ""

            val decodedDoctorJson = doctorJson?.let {
                URLDecoder.decode(it, StandardCharsets.UTF_8.toString())
            } ?: "{}"

            val doctor = Gson().fromJson(decodedDoctorJson, Doctor::class.java)

            BookingScreen(
                doctor = doctor,
                selectedDate = selectedDate,
                selectedTime = selectedTime,
                onNavigateBack = { navController.popBackStack() },
                onBookingConfirmed = { doc ->
                    Toast.makeText(context, "Booking Berhasil Dikonfirmasi!", Toast.LENGTH_LONG).show()
                    navController.popBackStack(AppDestinations.HOME_ROOT_ROUTE, inclusive = false)
                },
                onChangeDateClicked = {
                    navController.popBackStack()
                },
                onChangeReasonClicked = {
                    Toast.makeText(context, "Fitur ubah alasan masih dalam pengembangan.", Toast.LENGTH_SHORT).show()
                },
                onChangePaymentMethodClicked = {
                    Toast.makeText(context, "Fitur ubah metode pembayaran masih dalam pengembangan.", Toast.LENGTH_SHORT).show()
                },
                onChatDoctorClicked = { doctorId, chatPartnerName, chatPartnerImageRes ->
                    val encodedChatPartnerName = URLEncoder.encode(chatPartnerName, StandardCharsets.UTF_8.toString())
                    navController.navigate(
                        "${AppDestinations.CHAT_SCREEN_ROUTE}/" +
                                "$doctorId/" +
                                "$encodedChatPartnerName/" +
                                "$chatPartnerImageRes"
                    )
                }
            )
        }

        composable(
            route = "${AppDestinations.CHAT_SCREEN_ROUTE}/{chatPartnerId}/{chatPartnerName}/{chatPartnerImageRes}",
            arguments = listOf(
                navArgument("chatPartnerId") { type = NavType.StringType },
                navArgument("chatPartnerName") { type = NavType.StringType },
                navArgument("chatPartnerImageRes") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val chatPartnerId = backStackEntry.arguments?.getString("chatPartnerId")
            val chatPartnerName = backStackEntry.arguments?.getString("chatPartnerName")
            val chatPartnerImageRes = backStackEntry.arguments?.getInt("chatPartnerImageRes")

            ChatScreen(
                chatPartnerId = chatPartnerId,
                chatPartnerName = chatPartnerName,
                chatPartnerImageRes = chatPartnerImageRes,
                onNavigateBack = { navController.popBackStack() },
                onVideoCallClicked = { Toast.makeText(context, "Video Call feature in development", Toast.LENGTH_SHORT).show() },
                onVoiceCallClicked = { Toast.makeText(context, "Voice Call feature in development", Toast.LENGTH_SHORT).show() }
            )
        }

        composable(AppDestinations.MESSAGES_SCREEN_ROUTE) {
            MessagesScreen(
                onNavigateToChat = { conversationId, chatPartnerName, chatPartnerImageRes ->
                    val encodedChatPartnerName = URLEncoder.encode(chatPartnerName, StandardCharsets.UTF_8.toString())
                    navController.navigate(
                        "${AppDestinations.CHAT_SCREEN_ROUTE}/" +
                                "$conversationId/" +
                                "$encodedChatPartnerName/" +
                                "$chatPartnerImageRes"
                    )
                }
            )
        }

        composable(AppDestinations.SCHEDULE_SCREEN_ROUTE) {
            ScheduleScreen()
        }

        composable(AppDestinations.PROFILE_SCREEN_ROUTE) {
            ProfileScreen(
                painter = painterResource(R.drawable.profile_amelia_renata),
                onLogoutClicked = {
                    loggedInUserName = null
                    loggedInUserEmail = null
                    navController.navigate(AppDestinations.LOGIN_SIGNUP_ROUTE) {
                        popUpTo(AppDestinations.HOME_ROOT_ROUTE) { inclusive = true }
                    }
                    println("Logout berhasil dari Profile!")
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