package com.oxyhotels.admin.feature_manage_hotel.domain.use_cases

import com.oxyhotels.admin.feature_manage_hotel.domain.model.HotelStorage
import com.oxyhotels.admin.feature_manage_hotel.domain.repositry.HotelRepository
import kotlinx.coroutines.flow.Flow

class GetHotel(
    private val hotelRepository: HotelRepository
) {

    operator fun invoke():Flow<List<HotelStorage>> {
        return hotelRepository.getHotels()
    }
}