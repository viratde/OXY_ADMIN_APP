package com.oxyhotels.admin.feature_booking.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.oxyhotels.admin.feature_booking.domain.model.BookingModel


@Database(
    entities = [BookingModel::class],
    version = 3,
    exportSchema = false
)
@TypeConverters(BookingDatabaseTypeConverter::class)
abstract class BookingDatabase :RoomDatabase(){

    abstract val bookingDao: BookingDao

    companion object{
        const val DATABASE_NAME = "Booking Database"
    }

}