package com.oxyhotels.admin.feature_location.domain.models

data class Location(
    val latitude: Double,
    val longitude: Double,
    val distance: Double,
    val name: String,
    val _id: String
)
