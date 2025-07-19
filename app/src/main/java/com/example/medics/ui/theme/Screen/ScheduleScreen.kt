package com.example.medics.ui.theme.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medics.R
import com.example.medics.model.Doctor

enum class AppointmentStatus {
    Upcoming, Completed, Canceled
}

data class Appointment(
    val id: String,
    val doctor: Doctor,
    val date: String,
    val time: String,
    val status: AppointmentStatus,
    val isConfirmed: Boolean = true
)

@Composable
fun ScheduleScreen() {
    val allAppointments = remember {
        listOf(
            Appointment(
                id = "app1",
                doctor = Doctor("doc1", "Dr. Marcus Horizon", "Cardiologist", R.drawable.marcus),
                date = "26/06/2024",
                time = "10:30 AM",
                status = AppointmentStatus.Upcoming,
                isConfirmed = true
            ),
            Appointment(
                id = "app2",
                doctor = Doctor("doc2", "Dr. Alysa Hana", "Psikiater", R.drawable.dr_stevi_jessi),
                date = "28/06/2024",
                time = "02:00 PM",
                status = AppointmentStatus.Upcoming,
                isConfirmed = true
            ),
            Appointment(
                id = "app3",
                doctor = Doctor("doc3", "Dr. Walter White", "Oncologist", R.drawable.dr_maria_elena),
                date = "20/06/2024",
                time = "09:00 AM",
                status = AppointmentStatus.Completed,
                isConfirmed = true
            ),
            Appointment(
                id = "app4",
                doctor = Doctor("doc1", "Dr. Marcus Horizon", "Cardiologist", R.drawable.marcus),
                date = "25/06/2024",
                time = "03:00 PM",
                status = AppointmentStatus.Canceled,
                isConfirmed = false
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F7FF))
    ) {
        LazyColumn(
            modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(allAppointments) { appointment ->
            AppointmentCard(appointment = appointment)
            }
        }
    }
}


@Composable
fun AppointmentCard(appointment: Appointment) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = appointment.doctor.name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = appointment.doctor.specialization,
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
                Image(
                    painter = painterResource(id = appointment.doctor.imageRes ?: R.drawable.dr_marcus_horizon),
                    contentDescription = appointment.doctor.name,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.CalendarMonth,
                    contentDescription = "Date",
                    tint = Color.Gray,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = appointment.date, fontSize = 14.sp, color = Color.Gray)
                Spacer(modifier = Modifier.width(16.dp))
                Icon(
                    imageVector = Icons.Default.Schedule,
                    contentDescription = "Time",
                    tint = Color.Gray,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = appointment.time, fontSize = 14.sp, color = Color.Gray)
                Spacer(modifier = Modifier.width(16.dp))
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Confirmed",
                    tint = if (appointment.isConfirmed) Color(0xFF4CB29E) else Color.Red,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = if (appointment.isConfirmed) "Confirmed" else "Pending",
                    fontSize = 14.sp,
                    color = if (appointment.isConfirmed) Color(0xFF4CB29E) else Color.Red
                )
            }
            if (appointment.status == AppointmentStatus.Upcoming) {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { /* Handle Cancel */ },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0F3EF)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Cancel", color = Color.Black)
                    }
                    Button(
                        onClick = { },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF008080)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Reschedule", color = Color.White)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Schedule Screen Preview")
@Composable
fun ScheduleScreenPreview() {
    ScheduleScreen()
}
