package com.example.medics.ui.theme.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medics.R
import com.example.medics.model.Doctor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorDetailScreen(
    doctorId: String?,
    onNavigateBack: () -> Unit,
    onBookAppointmentClicked: (doctor: Doctor, selectedDate: String, selectedTime: String) -> Unit,
    onChatClicked: (doctorId: String) -> Unit
) {
    val doctor = remember(doctorId) {
        when (doctorId) {
            "doc1" -> Doctor("doc1", "Dr. Marcus Horizon", "Cardiologist", R.drawable.marcus, "City General Hospital", 4.8f, 230)
            "doc2" -> Doctor("doc2", "Dr. Maria Elena", "Skin Specialist", R.drawable.maria, "Metro Hospital", 4.5f, 98)
            "doc3" -> Doctor("doc3", "Dr. Stevi Jessi", "Heart Specialist", R.drawable.stefi, "Hope Clinic", 4.9f, 210)
            else -> Doctor("dummy", "Dr. Dummy Dokter", "General Practitioner", R.drawable.dr_marcus_horizon, "Dummy Clinic", 4.0f, 50)
        }
    }

    var selectedDateIndex by remember { mutableStateOf(2) }
    var selectedTimeIndex by remember { mutableStateOf(5) }

    val dates = remember {
        listOf(
            "Mon\n21", "Tue\n22", "Wed\n23", "Thu\n24", "Fri\n25", "Sat\n26", "Sun\n27"
        )
    }

    val times = remember {
        listOf(
            "09:00 AM", "10:00 AM", "11:00 AM", "12:00 PM", "01:00 PM",
            "02:00 PM", "03:00 PM", "04:00 PM", "05:00 PM", "06:00 PM",
            "07:00 PM", "08:00 PM", "09:00 PM"
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Doctor Detail", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "More options")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF008080),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = { onChatClicked(doctor.id) },
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFE0F3EF))
                ) {
                    Icon(
                        imageVector = Icons.Default.Chat,
                        contentDescription = "Chat with doctor",
                        tint = Color(0xFF008080),
                        modifier = Modifier.size(28.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = {
                        val selectedDateText = dates[selectedDateIndex].replace("\n", ", ")
                        val selectedTimeText = times[selectedTimeIndex]
                        onBookAppointmentClicked(doctor, selectedDateText, selectedTimeText)
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF008080)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Book Appointment", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .background(Color.White)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = doctor.imageRes),
                    contentDescription = doctor.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(12.dp))
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = doctor.name,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = doctor.specialization,
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = "Rating",
                            tint = Color(0xFFFFC107),
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "${doctor.rating}",
                            fontSize = 14.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Icon(
                            imageVector = Icons.Filled.LocationOn,
                            contentDescription = "Location",
                            tint = Color.Gray,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "800m away",
                            fontSize = 14.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "About",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                val longText = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
                var expanded by remember { mutableStateOf(false) }

                val annotatedText = buildAnnotatedString {
                    if (longText.length > 150 && !expanded) {
                        append(longText.substring(0, 150))
                        withStyle(style = SpanStyle(color = Color(0xFF008080), fontWeight = FontWeight.Bold)) {
                            append("... Read more")
                        }
                    } else {
                        append(longText)
                        if (longText.length > 150 && expanded) {
                            withStyle(style = SpanStyle(color = Color(0xFF008080), fontWeight = FontWeight.Bold)) {
                                append(" Read less")
                            }
                        }
                    }
                }

                Text(
                    text = annotatedText,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.clickable { expanded = !expanded }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(dates.size) { index ->
                    DateChip(
                        dateText = dates[index],
                        isSelected = index == selectedDateIndex,
                        onClick = { selectedDateIndex = index }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                for (i in times.indices step 3) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        for (j in 0..2) {
                            val timeIndex = i + j
                            if (timeIndex < times.size) {
                                TimeChip(
                                    timeText = times[timeIndex],
                                    isSelected = timeIndex == selectedTimeIndex,
                                    onClick = { selectedTimeIndex = timeIndex },
                                    modifier = Modifier.weight(1f)
                                )
                            } else {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun DateChip(dateText: String, isSelected: Boolean, onClick: () -> Unit) {
    val backgroundColor = if (isSelected) Color(0xFF008080) else Color(0xFFE0F3EF)
    val textColor = if (isSelected) Color.White else Color.Black
    val dayOfWeek = dateText.split("\n")[0]
    val dayOfMonth = dateText.split("\n")[1]

    Column(
        modifier = Modifier
            .width(60.dp)
            .height(80.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = dayOfWeek,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = textColor
        )
        Text(
            text = dayOfMonth,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = textColor
        )
    }
}

@Composable
fun TimeChip(timeText: String, isSelected: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val backgroundColor = if (isSelected) Color(0xFF008080) else Color(0xFFE0F3EF)
    val textColor = if (isSelected) Color.White else Color.Black

    Box(
        modifier = modifier
            .height(48.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = timeText,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = textColor
        )
    }
}


@Preview(showBackground = true, name = "Doctor Detail Screen Preview")
@Composable
fun DoctorDetailScreenPreview() {
    val sampleDoctor = Doctor("doc1", "Dr. Marcus Horizon", "Cardiologist", R.drawable.marcus, "City General Hospital", 4.7f, 230)
    DoctorDetailScreen(
        doctorId = "doc1",
        onNavigateBack = {},
        onBookAppointmentClicked = { doc, date, time ->
            println("Booked appointment for ${doc.name} on $date at $time")
        },
        onChatClicked = { docId ->
            println("Chat with doctor $docId")
        }
    )
}