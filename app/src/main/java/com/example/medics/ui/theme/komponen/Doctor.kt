package com.example.medics.ui.theme.komponen

import androidx.annotation.DrawableRes

data class Doctor(
    val id: String,
    val name: String,
    val specialization: String,
    @DrawableRes val imageRes: Int,
    val hospital: String,
    val rating: Float,
    val reviews: Int
)