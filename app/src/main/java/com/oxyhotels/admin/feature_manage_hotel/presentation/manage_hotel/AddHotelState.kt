package com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel

data class AddHotelState(
    val isError:Boolean = false,
    val errorMessage:String = "",
    val isLoading:Boolean = false,
    val isSaved:Boolean? = null
)