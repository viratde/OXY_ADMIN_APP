package com.oxyhotels.admin.constant

object Constant {

    const val domain = "http://192.168.70.190:3000"
    const val authRoute = "/admin/auth"
    const val getAllHotelsRoute= "$domain/admin/getHotels"

    const val getAnalytics = "$domain/admin/getAnalytics"

    const val addHotelRoute ="$domain/admin/createHotel"
    const val addHotelImagesRoute = "$domain/admin/upload"
    const val updateHotelRoute = "$domain/admin/updateHotel"

    const val checkInUpdateRoute = "$domain/admin/checkInUpdateGuest"
    const val checkInRoute = "$domain/admin/actionInGuest"
    const val extraPriceUpdate: String = "$domain/admin/extraPriceUpdate"
    const val getPendingPaymentBookings = "$domain/admin/getPendingPaymentBookings"
    const val clearPendingPaymentBookings = "$domain/admin/clearPendingPaymentBookings"


    const val inHouseRoute = "$domain/admin/getInHouseBooking"
    const val allDataLoadedOnceRoute = "$domain/admin/getAllBookings"
    const val upcomingHouseRoute = "$domain/admin/getUpcomingBooking"
    const val completeHouseRoute = "$domain/admin/getCompleteBooking"
    const val createBookingRoute = "$domain/admin/createBooking"
    const val getUserDataRoute = "$domain/admin/getUserData"
    const val getBookingByBookingIdRoute = "$domain/admin/getBookingByBookingIdRoute"
}