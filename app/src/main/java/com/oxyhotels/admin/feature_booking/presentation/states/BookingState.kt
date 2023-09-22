package com.oxyhotels.admin.feature_booking.presentation.states

import com.oxyhotels.admin.feature_booking.domain.model.BookingModel

data class BookingState(
    val bookingModel: BookingModel,
    val isLoading:Boolean = false,
    val totalPrice:Int,
    val isLoaded:Boolean =false,
    val isError:Boolean = false,
    val errorMessage : String = "",
    val isCheckingInActive:Boolean = false,
    val isCheckingOutActive:Boolean = false,
    val isCheckingOutOtpSending:Boolean = false,
    val otpToken:String = "",
    val isCheckingIn:Boolean = false,
    val isCheckingOut:Boolean = false,
    val isCancelling: Boolean = false,
    val isNotShowing:Boolean = false,
    val checkInRequest: CheckInRequest,
    val extraPaymentRequest: ExtraPaymentRequest
)

