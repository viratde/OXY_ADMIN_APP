package com.oxyhotels.admin.feature_booking.presentation.states

data class CheckInRequest(
    val floorNo:String = "0",
    val roomNo:String = "0",
    val registerNo:String = "0",
    val cash:String = "0",
    val bank:String = "0",
    val pending:String = "0",
    val ota:String = "0",
)