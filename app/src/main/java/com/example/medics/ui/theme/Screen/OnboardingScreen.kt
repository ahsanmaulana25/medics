package com.example.medics.ui.theme.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medics.R

@Composable
fun OnboardingScreen(
    imageRes: Int,
    caption: String,
    onNextClicked: () -> Unit,
    onSkipClicked: () -> Unit
) {
    val newSharedColor = Color(0xFFF5F7FF)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp)
    ){
        Text(
            text = "Skip",
            fontSize = 16.sp,
            color = Color.Black,
            fontWeight = FontWeight.Normal,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(horizontal = 16.dp)
                .clickable(onClick = onSkipClicked)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = "Doctor Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.77f)
                    .clickable { onNextClicked() }
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minWidth = 150.dp)
                    .clip(RoundedCornerShape(12.dp, bottomEnd = 12.dp))
                    .background(newSharedColor)
                    .padding(16.dp)
            ) {
                Text(
                    text = caption,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Left,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(bottom = if (caption.length > 60) 56.dp else 150.dp)
                )
                Button(
                    onClick = onNextClicked,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(56.dp),
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF008080)
                    ),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = if (imageRes == R.drawable.dokter_3_onboard) "Get Started" else "Next",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}