package com.oxyhotels.admin.feature_booking.domain.use_cases

import com.oxyhotels.admin.feature_booking.domain.model.BookingModel
import com.oxyhotels.admin.feature_booking.domain.repository.BookingRepository

class UpdateBookingUseCase (
    private val bookingRepository: BookingRepository
        ) {

    suspend operator fun invoke(booking : BookingModel){
        return bookingRepository.addBooking(booking = booking)
    }

}