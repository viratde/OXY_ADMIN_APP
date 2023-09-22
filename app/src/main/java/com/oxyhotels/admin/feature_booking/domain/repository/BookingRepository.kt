package com.oxyhotels.admin.feature_booking.domain.repository

import com.oxyhotels.admin.feature_booking.domain.model.BookingModel
import kotlinx.coroutines.flow.Flow

interface BookingRepository {


    suspend fun addBookings( bookings : List<BookingModel>)

    suspend fun getBookings() : Flow<List<BookingModel>>

    suspend fun getBookingById(bookingId:Int) : BookingModel

    suspend fun clearBookings()

    suspend fun deleteBookingById(bookingId: Int)

    suspend fun addBooking(booking: BookingModel)

}
