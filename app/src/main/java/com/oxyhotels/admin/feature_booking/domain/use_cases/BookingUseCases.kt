package com.oxyhotels.admin.feature_booking.domain.use_cases

import com.oxyhotels.admin.feature_booking.domain.use_cases.AddBookingUseCases
import com.oxyhotels.admin.feature_booking.domain.use_cases.ClearBookingUseCases
import com.oxyhotels.admin.feature_booking.domain.use_cases.DeleteBookingByBookingId
import com.oxyhotels.admin.feature_booking.domain.use_cases.GetBookingByBookingId
import com.oxyhotels.admin.feature_booking.domain.use_cases.GetBookingsUseCases
import com.oxyhotels.admin.feature_booking.domain.use_cases.UpdateBookingUseCase


data class BookingUseCases(
    val getBookingsUseCases: GetBookingsUseCases,
    val addBookingUseCases: AddBookingUseCases,
    val clearBookingUseCases: ClearBookingUseCases,
    val deleteBookingByBookingId: DeleteBookingByBookingId,
    val updateBookingUseCase: UpdateBookingUseCase,
    val getBookingByBookingId: GetBookingByBookingId
)