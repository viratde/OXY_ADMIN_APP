package com.oxyhotels.admin.feature_manage_hotel.presentation.hotels

import com.oxyhotels.admin.feature_manage_hotel.domain.model.HotelStorage

data class HotelsState (
    val hotels:List<HotelStorage> = listOf(),
    val isRemoteHotelsLoaded:Boolean=false,
    val isEvent:Boolean=false,
    val eventMessage:String="",
    val isRemoteHotelsLoading:Boolean=false,
    )