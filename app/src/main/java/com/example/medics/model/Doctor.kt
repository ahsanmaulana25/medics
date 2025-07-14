package com.example.medics.model

import androidx.annotation.DrawableRes

data class Doctor(
    val id: String,
    val name: String,
    val specialization: String,
    @DrawableRes val imageRes: Int,
    val hospital: String? = null,
    val rating: Float? = null,
    val reviews: Int? = null
)