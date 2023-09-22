package com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.states



data class AddBasicDetailState (
    val hotelName:String="",
    val hotelId:String="",
    val phoneNo:String="",
    val latitude:String="",
    val longitude:String="",
    val locationUrl:String="",
    val hotelAddress:String = "",
    val hotelDescription:String="",
    var refundPercentage:String ="0",
    val checkInTime:String = "12-00",
    val checkOutTime :String = "11-00",
    val minPrice :Int = 0,
    val maxPrice:Int = 0,
)