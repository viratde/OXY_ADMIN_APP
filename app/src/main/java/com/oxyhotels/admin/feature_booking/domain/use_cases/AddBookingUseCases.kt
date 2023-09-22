package com.oxyhotels.admin.feature_booking.domain.use_cases

import com.oxyhotels.admin.feature_booking.domain.model.BookingModel
import com.oxyhotels.admin.feature_booking.domain.repository.BookingRepository

class AddBookingUseCases (
    private val bookingRepository: BookingRepository
    ) {

    suspend operator fun invoke(bookings : List<BookingModel>){
        return bookingRepository.addBookings(bookings = bookings)
    }



}