package com.oxyhotels.admin.feature_manage_hotel.domain.repositry

import com.oxyhotels.admin.feature_manage_hotel.domain.model.HotelStorage
import kotlinx.coroutines.flow.Flow

interface HotelRepository {

    fun getHotels():Flow<List<HotelStorage>>

    suspend fun getHotelById(hotelId:String): HotelStorage?

    suspend fun addHotel(hotelStorage: HotelStorage)

    suspend fun deleteHotel(hotelStorage: HotelStorage)

    suspend fun addHotels(hotelStorages: List<HotelStorage>)

    suspend fun clearHotels()
}