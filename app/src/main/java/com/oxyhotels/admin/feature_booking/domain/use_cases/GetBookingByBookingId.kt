package com.oxyhotels.admin.feature_booking.domain.use_cases

import com.oxyhotels.admin.feature_booking.domain.model.BookingModel
import com.oxyhotels.admin.feature_booking.domain.repository.BookingRepository


class GetBookingByBookingId(
    private val bookingRepository: BookingRepository
) {

    suspend operator fun invoke(bookingId:Int) : BookingModel?{
        return bookingRepository.getBookingById(bookingId = bookingId)
    }
}