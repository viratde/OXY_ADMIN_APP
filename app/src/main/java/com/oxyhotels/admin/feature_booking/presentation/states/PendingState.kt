package com.oxyhotels.admin.feature_booking.presentation.states

import com.oxyhotels.admin.feature_booking.domain.model.BookingModel
import com.oxyhotels.admin.feature_booking.presentation.states.ClearPendingState

data class PendingState(
    val bookings: List<BookingModel> = listOf(),
    val isError: Boolean = false,
    val errorMessage: String = "",
    val isLoading: Boolean = false,
    val loadingBookingId: Int? = null,
    val isDataLoading: Boolean = false,
    val clearPendingState: ClearPendingState = ClearPendingState()
)