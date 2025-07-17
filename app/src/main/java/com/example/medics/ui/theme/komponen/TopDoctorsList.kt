package com.example.medics.ui.theme.komponen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.medics.R
import com.example.medics.model.Doctor

@Composable
fun TopDoctorsList(
    doctors: List<Doctor>,
    onDoctorCardClicked: (doctorId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(start = 12.dp, end = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(doctors, key = { it.id }) { doctor ->
            DoctorCard(
                doctor = doctor,
                onClick = onDoctorCardClicked
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopDoctorsListPreview() {
    val sampleDoctors = listOf(
        Doctor("1", "Dr. Amelia Renata", "Cardiologist", R.drawable.dr_marcus_horizon, "City Hospital", 4.8f, 120)
    )
    TopDoctorsList(doctors = sampleDoctors, onDoctorCardClicked = {})
}