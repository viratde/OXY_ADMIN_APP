package com.oxyhotels.admin.feature_manage_hotel.data.data_source

import androidx.room.*
import com.oxyhotels.admin.feature_manage_hotel.domain.model.HotelStorage
import kotlinx.coroutines.flow.Flow


@Dao
interface HotelDao {


    @Query("SELECT * from hotelStorage")
    fun getHotels():Flow<List<HotelStorage>>

    @Query("SELECT * from hotelStorage where _id=:hotelId")
    fun getHotelById(hotelId:String): HotelStorage?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addHotel(hotelStorage: HotelStorage)

    @Delete
    fun deleteHotel(hotelStorage: HotelStorage)

    @Insert
    fun addHotels(hotelStorages: List<HotelStorage>)

    @Query("Delete from hotelStorage")
    fun clearHotels()

}