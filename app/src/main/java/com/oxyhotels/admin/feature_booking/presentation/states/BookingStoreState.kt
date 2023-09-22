package com.oxyhotels.admin.feature_booking.presentation.states

import com.oxyhotels.admin.feature_booking.domain.model.BookingModel

data class BookingStoreState(
    val inHouseBookings : List<BookingModel> = listOf(),
    val upcomingBookings : List<BookingModel> = listOf(),
    val completeBookings : List<BookingModel> = listOf(),
    val isInHouseLoading:Boolean = false,
    val isInHouseLoaded:Boolean = false,
    val isUpcomingLoading:Boolean = false,
    val isUpcomingLoaded: Boolean = false,
    val isCompleteLoading:Boolean = false,
    val isCompleteLoaded: Boolean = false,
    val isError:Boolean = false,
    val errorMessage:String = "",
    val selectedDate:String
)