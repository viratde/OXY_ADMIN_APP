package com.oxyhotels.admin.constant

object Constant {

    const val DOMAIN = "https://api.oxyhotels.com"

    const val authRoute = "/admin/auth"
    const val getAllHotelsRoute= "$DOMAIN/admin/getAdminHotels"

    const val addHotelRoute ="$DOMAIN/admin/addHotel"
    const val addHotelImagesRoute = "$DOMAIN/admin/upload"
    const val updateHotelRoute = "$DOMAIN/admin/updateHotel"

    const val getAllManagersRoute = "$DOMAIN/admin/getAllManagers"

    const val updateManager = "$DOMAIN/admin/updateManager"

    const val getAllLocationsRoute = "$DOMAIN/admin/getAllLocations"

    const val updateLocationRoute = "$DOMAIN/admin/updateLocation"
}