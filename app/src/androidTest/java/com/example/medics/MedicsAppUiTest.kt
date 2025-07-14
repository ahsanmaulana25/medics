package com.example.medics

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlinx.coroutines.test.runTest
import org.junit.Before
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.hasAnyChild
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.onNodeWithTag

@RunWith(AndroidJUnit4::class)
class MedicsAppUiTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setup() {
        composeTestRule.setContent {
            MedicsApp()
        }
    }

    @Test
    fun app_starts_on_splash_and_navigates_to_onboarding() = runTest {
        composeTestRule.onNodeWithContentDescription("Logo").assertExists("Splash screen logo should be visible")
        composeTestRule.onNodeWithContentDescription("Logo").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Skip").assertExists("Onboarding screen should be visible")
    }

    @Test
    fun onboarding_navigates_to_login_signup() = runTest {
        app_starts_on_splash_and_navigates_to_onboarding()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Skip").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Let's get started!").assertExists("LoginOrSignUp screen should be visible")
        composeTestRule.onNodeWithText("Login").assertExists()
        composeTestRule.onNodeWithText("Sign Up").assertExists()
    }

    @Test
    fun login_flow_success() = runTest {
        onboarding_navigates_to_login_signup()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Login").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Email Address").performTextInput("test@example.com")
        composeTestRule.onNodeWithText("Password").performTextInput("password123")
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Sign In").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Medics App").assertExists("Should navigate to Home Screen after successful login")
        composeTestRule.onNodeWithText("Home").assertExists("Home tab should be visible")
    }

    @Test
    fun signup_flow_success() = runTest {
        onboarding_navigates_to_login_signup()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Sign Up").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Full Name").performTextInput("John Doe")
        composeTestRule.onNodeWithText("Email Address").performTextInput("john.doe@example.com")
        composeTestRule.onNodeWithText("Password").performTextInput("securepassword")
        composeTestRule.onNodeWithText("Confirm Password").performTextInput("securepassword")
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("I agree to the Terms & Conditions").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Sign Up").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Medics App").assertExists("Should navigate to Home Screen after successful signup")
        composeTestRule.onNodeWithText("Home").assertExists("Home tab should be visible") // Pastikan tab Home aktif
    }

    @Test
    fun navigate_to_top_doctors_from_home_see_all() = runTest {
        login_flow_success()
        composeTestRule.waitForIdle()
        composeTestRule.onNode(hasText("Top Doctors", substring = true) and hasAnyChild(hasText("See All")))
            .onChildren()
            .filterToOne(hasText("See All"))
            .performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Top Doctors").assertExists("Top Doctors Screen should be visible")
        composeTestRule.onNodeWithContentDescription("Back").assertExists("Back button should be visible")
    }

    @Test
    fun navigate_to_top_doctors_from_doctor_shortcut() = runTest {
        login_flow_success()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Doctor").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Top Doctors").assertExists("Top Doctors Screen should be visible after clicking Doctor shortcut")
        composeTestRule.onNodeWithContentDescription("Back").assertExists("Back button should be visible")
    }

    @Test
    fun navigate_back_from_top_doctors_screen() = runTest {
        navigate_to_top_doctors_from_home_see_all()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithContentDescription("Back").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Medics App").assertExists("Should return to Home Screen")
        composeTestRule.onNodeWithText("Top Doctors").assertExists()
        composeTestRule.onNodeWithText("Home").assertExists()
    }

    @Test
    fun bottom_navigation_to_messages_screen() = runTest {
        login_flow_success()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Messages").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("MessagesScreenHeader").assertExists("Messages screen header should be visible")
        composeTestRule.onNodeWithText("No messages yet.").assertExists()
    }

    @Test
    fun bottom_navigation_to_schedule_screen() = runTest {
        login_flow_success()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Schedule").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Schedule Masih Dalam Pengembangan").assertExists("Schedule screen should be visible")
    }

    @Test
    fun bottom_navigation_to_profile_screen() = runTest {
        login_flow_success()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Profile").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Amelia Renata").assertExists("Profile screen should be visible")
    }
}