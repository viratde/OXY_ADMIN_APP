package com.oxyhotels.admin.feature_booking.presentation.states

import com.oxyhotels.admin.feature_booking.presentation.states.CreateBooking

data class CreateBookingState(
    val booking: CreateBooking,
    val isLoading:Boolean = false,
    val isLoaded:Boolean =false,
    val isError:Boolean = false,
    val errorMessage : String = "",
)
