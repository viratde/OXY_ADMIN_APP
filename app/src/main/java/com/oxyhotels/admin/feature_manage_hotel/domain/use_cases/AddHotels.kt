package com.oxyhotels.admin.feature_manage_hotel.domain.use_cases

import com.oxyhotels.admin.feature_manage_hotel.domain.model.HotelStorage
import com.oxyhotels.admin.feature_manage_hotel.domain.repositry.HotelRepository

class AddHotels(
    private val hotelRepository: HotelRepository
) {

    suspend operator fun invoke(hotelStorages: List<HotelStorage>){
        hotelRepository.addHotels(hotelStorages)
    }
}