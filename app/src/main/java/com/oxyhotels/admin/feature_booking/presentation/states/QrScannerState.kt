package com.oxyhotels.admin.feature_booking.presentation.states

import com.oxyhotels.admin.feature_booking.domain.model.BookingModel

data class QrScannerState(
    val isLoading:Boolean = false,
    val isError:Boolean  = false,
    val isInstalling:Boolean = false,
    val progressState : Int = 0,
    val errorMessage:String = "",
    val bookingModel: BookingModel?,
    val bookingId:String? = null
)