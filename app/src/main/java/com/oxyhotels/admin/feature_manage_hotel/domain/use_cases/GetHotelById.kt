package com.oxyhotels.admin.feature_manage_hotel.domain.use_cases

import com.oxyhotels.admin.feature_manage_hotel.domain.model.HotelStorage
import com.oxyhotels.admin.feature_manage_hotel.domain.repositry.HotelRepository

class GetHotelById(
    private val hotelRepository: HotelRepository
) {

    suspend operator fun invoke(hotelId:String): HotelStorage?{
        return hotelRepository.getHotelById(hotelId)
    }

}