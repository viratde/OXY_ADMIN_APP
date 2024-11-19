package com.oxyhotels.admin.feature_location.presentation.states

import com.oxyhotels.admin.feature_location.domain.models.Location

data class LocationsState(
    val locations: List<Location> = listOf(),
    val isLocationsLoading: Boolean = false,
    val isLocationsLoaded: Boolean = false,

    val isLocationUpdating: Boolean = false,

    val isError: Boolean = false,
    val errorMessage: String = ""
)