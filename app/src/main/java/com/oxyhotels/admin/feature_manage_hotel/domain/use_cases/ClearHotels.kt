package com.oxyhotels.admin.feature_manage_hotel.domain.use_cases

import com.oxyhotels.admin.feature_manage_hotel.domain.repositry.HotelRepository

class ClearHotels(
    private val hotelRepository: HotelRepository
) {

    suspend operator fun invoke(){
        hotelRepository.clearHotels()
    }
}