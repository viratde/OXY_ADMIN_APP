package com.oxyhotels.admin.feature_manage_hotel.presentation.pricing.utils

data class PricingRequest (
    val startDate:String = "",
    val endDate:String = "",
    val pax1Price:Int = 0,
    val pax2Price:Int = 0,
    val pax3Price:Int = 0,
    val roomType:String = "",
)