package com.example.medics.ui.theme.Screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.medics.R
import com.example.medics.model.Doctor
import com.example.medics.ui.theme.komponen.DoctorCardHorizontal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopDoctorsScreen(
    onNavigateBack: () -> Unit,
    onDoctorCardClicked: (doctorId: String) -> Unit
) {
    val allDoctorsSample = remember {
        listOf(
            Doctor(
                id = "doc1",
                name = "Dr. Marcus Horizon",
                specialization = "Dental Specialist",
                imageRes = R.drawable.marcus,
                hospital = "Singaparna Hospital",
                rating = 4.7f,
                reviews = 120
            ),
            Doctor(
                id = "doc2",
                name = "Dr. Maria Elena",
                specialization = "Skin Specialist",
                imageRes = R.drawable.maria,
                hospital = "Metro Hospital",
                rating = 4.5f,
                reviews = 98
            ),
            Doctor(
                id = "doc3",
                name = "Dr. Stevi Jessi",
                specialization = "Heart Specialist",
                imageRes = R.drawable.stefi,
                hospital = "Hope Clinic",
                rating = 4.9f,
                reviews = 210
            ),
            Doctor(
                id = "doc4",
                name = "Dr. Gerty Cori",
                specialization = "Pediatrician",
                imageRes = R.drawable.gerty,
                hospital = "Children's Hospital",
                rating = 4.6f,
                reviews = 150
            ),
            Doctor(
                id = "doc5",
                name = "Dr. Alan Turing",
                specialization = "Neurologist",
                imageRes = R.drawable.marcus,
                hospital = "Brain & Spine Center",
                rating = 4.8f,
                reviews = 180
            ),
            Doctor(
                id = "doc6",
                name = "Dr. Grace Hopper",
                specialization = "Oncologist",
                imageRes = R.drawable.diandra,
                hospital = "Cancer Treatment Unit",
                rating = 4.9f,
                reviews = 250
            ),
            Doctor(
                id = "doc7",
                name = "Dr. Albert Einstein",
                specialization = "Physicist",
                imageRes = R.drawable.gerty,
                hospital = "Research Institute",
                rating = 4.0f,
                reviews = 50
            )
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Top Doctors", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF008080),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(allDoctorsSample) { doctor ->
                DoctorCardHorizontal(
                    doctor = doctor,
                    onClick = onDoctorCardClicked,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "Top Doctors Screen Preview")
@Composable
fun TopDoctorsScreenPreview() {
    TopDoctorsScreen(
        onNavigateBack = {},
        onDoctorCardClicked = {}
    )
}