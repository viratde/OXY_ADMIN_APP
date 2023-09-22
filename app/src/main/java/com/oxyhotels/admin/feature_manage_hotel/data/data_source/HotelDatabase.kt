package com.oxyhotels.admin.feature_manage_hotel.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.oxyhotels.admin.feature_manage_hotel.domain.model.HotelStorage


@Database(
    entities = [HotelStorage::class],
    version = 4,
    exportSchema = false
)
@androidx.room.TypeConverters(TypeConverters::class)
abstract class HotelDatabase :RoomDatabase(){
    abstract val hotelDao: HotelDao

    companion object {
        const val DATABASE_NAME="hotels_db"
    }
}