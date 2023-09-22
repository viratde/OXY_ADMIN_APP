package com.oxyhotels.admin.feature_booking.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.oxyhotels.admin.feature_booking.domain.model.BookingModel
import kotlinx.coroutines.flow.Flow


@Dao
interface BookingDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addBookings( bookings : List<BookingModel> )

    @Query("SELECT * FROM `Bookings Storage`")
    fun getBookings() : Flow<List<BookingModel>>

    @Query("SELECT * FROM `Bookings Storage` WHERE bookingId=:bookingId")
    fun getBookingById(bookingId: Int): BookingModel

    @Query("DELETE FROM `Bookings Storage`")
    fun clearBookings()

    @Query("DELETE FROM `Bookings Storage` WHERE bookingId=:bookingId")
    fun deleteBookingById(bookingId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateBooking(booking: BookingModel)
}