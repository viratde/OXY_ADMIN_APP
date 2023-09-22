package com.oxyhotels.admin.feature_booking.data.repository

import com.oxyhotels.admin.feature_booking.data.data_source.BookingDao
import com.oxyhotels.admin.feature_booking.domain.model.BookingModel
import com.oxyhotels.admin.feature_booking.domain.repository.BookingRepository
import kotlinx.coroutines.flow.Flow

class BookingRepositoryImpl(
    private val bookingDao: BookingDao
): BookingRepository {

    override suspend fun addBookings(bookings: List<BookingModel>) {
        bookingDao.addBookings(bookings = bookings)
    }

    override suspend fun getBookings(): Flow<List<BookingModel>> {
        return bookingDao.getBookings()
    }

    override suspend fun getBookingById(bookingId: Int): BookingModel {
        return bookingDao.getBookingById(bookingId = bookingId)
    }

    override suspend fun clearBookings() {
        bookingDao.clearBookings()
    }

    override suspend fun deleteBookingById(bookingId: Int) {
        bookingDao.deleteBookingById(bookingId = bookingId)
    }

    override suspend fun addBooking(booking: BookingModel) {
        bookingDao.updateBooking(booking = booking)
    }

}