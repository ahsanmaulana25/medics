package com.example.medics.ui.theme.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.medics.R

@Composable
fun SplashScreen(onNextScreen: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF008080))
            .clickable { onNextScreen() },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_1),
            contentDescription = "Logo",
            modifier = Modifier
                .fillMaxWidth(0.3f)
                .aspectRatio(0.4f)
        )
    }
}

@Preview(showBackground = true, name = "Splash Screen Preview")
@Composable
fun SplashScreenPreviewSeparate() {
    SplashScreen(onNextScreen = {})
}