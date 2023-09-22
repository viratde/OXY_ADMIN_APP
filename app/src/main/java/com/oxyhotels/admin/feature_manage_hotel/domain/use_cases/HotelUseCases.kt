package com.oxyhotels.admin.feature_manage_hotel.domain.use_cases

import com.oxyhotels.admin.feature_manage_hotel.domain.use_cases.AddHotel
import com.oxyhotels.admin.feature_manage_hotel.domain.use_cases.AddHotels
import com.oxyhotels.admin.feature_manage_hotel.domain.use_cases.ClearHotels
import com.oxyhotels.admin.feature_manage_hotel.domain.use_cases.GetHotel
import com.oxyhotels.admin.feature_manage_hotel.domain.use_cases.GetHotelById


data class HotelUseCases(
    val addHotel: AddHotel,
    val getHotel: GetHotel,
    val addHotels: AddHotels,
    val clearHotels: ClearHotels,
    val getHotelById: GetHotelById
)