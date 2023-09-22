package com.oxyhotels.admin.feature_manage_hotel.data.repositry

import com.oxyhotels.admin.feature_manage_hotel.data.data_source.HotelDao
import com.oxyhotels.admin.feature_manage_hotel.domain.model.HotelStorage
import com.oxyhotels.admin.feature_manage_hotel.domain.repositry.HotelRepository
import kotlinx.coroutines.flow.Flow

class HotelRepositoryImpl(
    private val hotelDao: HotelDao
) : HotelRepository {

    override fun getHotels(): Flow<List<HotelStorage>> {
        return hotelDao.getHotels()
    }

    override suspend fun getHotelById(hotelId: String): HotelStorage? {
        return hotelDao.getHotelById(hotelId = hotelId)
    }

    override suspend fun addHotel(hotelStorage: HotelStorage) {
        hotelDao.addHotel(hotelStorage)
    }

    override suspend fun deleteHotel(hotelStorage: HotelStorage) {
        hotelDao.deleteHotel(hotelStorage)
    }

    override suspend fun clearHotels() {
        hotelDao.clearHotels()
    }

    override suspend fun addHotels(hotelStorages: List<HotelStorage>) {
        hotelDao.addHotels(hotelStorages)
    }

}