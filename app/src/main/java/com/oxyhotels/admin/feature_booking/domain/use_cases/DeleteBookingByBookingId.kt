package com.oxyhotels.admin.feature_booking.domain.use_cases

import com.oxyhotels.admin.feature_booking.domain.repository.BookingRepository

class DeleteBookingByBookingId(
    private val bookingRepository: BookingRepository
    ) {
    suspend operator fun invoke(bookingId:Int)  {
        return bookingRepository.deleteBookingById(bookingId = bookingId)
    }
}


