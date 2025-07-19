package com.example.medics.ui.theme.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medics.R

@Composable
fun LoginOrSignUpScreen(
    onLoginClicked: () -> Unit,
    onSignUpClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_2),
            contentDescription = "App Logo",
            modifier = Modifier
                .fillMaxWidth(0.45f)
                .aspectRatio(0.6f)
        )

        Text(
            text = "Let's get started!",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Text(
            text = "Login to enjoy the features we’ve provided, and stay healthy!",
            fontSize = 16.sp,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Button(
            onClick = onLoginClicked,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(vertical = 6.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF008080),
                contentColor = Color.White
            )
        ) {
            Text(text = "Login", fontSize = 16.sp)
        }

        Button(
            onClick = onSignUpClicked,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(vertical = 6.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF008080),
                contentColor = Color.White
            )
        ) {
            Text(text = "Sign Up", fontSize = 16.sp)
        }
    }
}

@Preview(showBackground = true, name = "Login or Sign Up Page")
@Composable
fun LoginOrSignUpPagePreview() {
    LoginOrSignUpScreen(
        onLoginClicked = {},
        onSignUpClicked = {}
    )
}