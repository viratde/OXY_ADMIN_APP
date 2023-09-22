package com.oxyhotels.admin.feature_booking.domain.use_cases

import com.oxyhotels.admin.feature_booking.domain.model.BookingModel
import com.oxyhotels.admin.feature_booking.domain.repository.BookingRepository
import kotlinx.coroutines.flow.Flow

class GetBookingsUseCases(
    private val bookingRepository: BookingRepository
) {

    suspend operator fun invoke() :Flow<List<BookingModel>> {
        return bookingRepository.getBookings()
    }
}