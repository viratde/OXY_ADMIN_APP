package com.oxyhotels.admin.feature_booking.domain.use_cases

import com.oxyhotels.admin.feature_booking.domain.repository.BookingRepository

class ClearBookingUseCases (
    private val bookingRepository: BookingRepository
    ) {

    suspend operator fun invoke ( ) {
        bookingRepository.clearBookings()
    }

}