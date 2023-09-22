package com.oxyhotels.admin.feature_booking.presentation.states

data class ClearPendingState(
    val cash:String = "0",
    val bank:String = "0",
    val ota:String = "0",
)