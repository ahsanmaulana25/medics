package com.example.medics.ui.theme.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medics.R
import com.example.medics.ui.theme.komponen.StatItem
import com.example.medics.ui.theme.komponen.VerticalDivider

@Composable
fun ProfileScreen(
    painter: Painter,
    onLogoutClicked: () -> Unit
) {
    val backgroundGreen = Color(0xFF4CB29E)
    val whiteSurface = Color(0xFFF8FAFD)
    val iconBg = Color(0xFFE0F3EF)
    val logoutRed = Color(0xFFE94A4A)

    var showDevelopmentDialog by remember { mutableStateOf(false) }
    val developmentMessage = "Fitur ini masih dalam tahap pengembangan."

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundGreen)
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box {
                Image(
                    painter = painterResource(R.drawable.profile_amelia_renata),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Color.White, CircleShape)
                        .align(Alignment.BottomEnd)
                        .padding(4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Edit Picture",
                        tint = backgroundGreen,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                "Amelia Renata",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            StatItem(
                icon = Icons.Filled.Favorite,
                iconTint = Color.White,
                title = "Heart rate",
                value = "215bpm",
                modifier = Modifier.weight(1f)
            )
            VerticalDivider(color = Color.White.copy(alpha = 0.5f))
            StatItem(
                icon = Icons.Filled.LocalFireDepartment,
                iconTint = Color.White,
                title = "Calories",
                value = "756cal",
                modifier = Modifier.weight(1f)
            )
            VerticalDivider(color = Color.White.copy(alpha = 0.5f))
            StatItem(
                icon = Icons.Filled.FitnessCenter,
                iconTint = Color.White,
                title = "Weight",
                value = "103lbs",
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)),
            color = whiteSurface,
            tonalElevation = 4.dp
        ) {
            Column(modifier = Modifier.padding(top = 16.dp)) {
                MenuItem(
                    painter = painterResource(id = R.drawable.my_saved_logo),
                    iconBackground = iconBg,
                    text = "My Saved",
                    onClick = { showDevelopmentDialog = true }
                )
                MenuItem(
                    painter = painterResource(id = R.drawable.appointment),
                    iconBackground = iconBg,
                    text = "Appointment",
                    onClick = { showDevelopmentDialog = true }
                )
                MenuItem(
                    painter = painterResource(id = R.drawable.payment_logo),
                    iconBackground = iconBg,
                    text = "Payment Method",
                    onClick = { showDevelopmentDialog = true }
                )
                MenuItem(
                    painter = painterResource(id = R.drawable.faq_s),
                    iconBackground = iconBg,
                    text = "FAQs",
                    onClick = { showDevelopmentDialog = true }
                )
                MenuItem(
                    painter = painterResource(id = R.drawable.logout_logo),
                    iconBackground = logoutRed.copy(alpha = 0.15f),
                    text = "Logout",
                    textColor = logoutRed,
                    onClick = onLogoutClicked
                )
            }
        }
    }

    if (showDevelopmentDialog) {
        AlertDialog(
            onDismissRequest = { showDevelopmentDialog = false },
            title = { Text(text = "Informasi") },
            text = { Text(text = developmentMessage) },
            confirmButton = {
                TextButton(onClick = { showDevelopmentDialog = false }) {
                    Text("OK")
                }
            }
        )
    }
}

@Composable
fun MenuItem(
    painter: Painter,
    iconBackground: Color,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    showDivider: Boolean = true,
    iconTint: Color? = null
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(iconBackground),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painter,
                    contentDescription = text,
                    modifier = Modifier.size(24.dp),
                    colorFilter = if (iconTint != null) ColorFilter.tint(iconTint) else null
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = text,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = textColor,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Navigate",
                tint = Color.Gray
            )
        }
        if (showDivider) {
            HorizontalDivider(
                modifier = Modifier.padding(start = 24.dp + 40.dp + 16.dp, end = 24.dp),
                thickness = 0.5.dp,
                color = Color.LightGray.copy(alpha = 0.5f)
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(
        painter = painterResource(R.drawable.profile_amelia_renata),
        onLogoutClicked = {}
    )
}