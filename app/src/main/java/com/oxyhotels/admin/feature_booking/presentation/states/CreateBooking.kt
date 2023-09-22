package com.oxyhotels.admin.feature_booking.presentation.states

import com.oxyhotels.admin.feature_booking.domain.model.BookedRoom

data class CreateBooking(
    val name:String = "",
    val phone:String = "",
    val email:String = "",
    val checkInTime:String,
    val checkOutTime:String,
    val referenceId :String = "",
    val price:String = "",
    val bookingSource:String = "Walking",
    val isConvenience:Boolean = false,
    val convenienceData :String = "",
    val isAdvancedPayment:Boolean = false,
    val advancedPaymentMethod:String = "Cash",
    val advancedPaymentData:String = "",
    val rooms:MutableList<BookedRoom> = mutableListOf()
)
