package com.oxyhotels.admin.feature_manage_hotel.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.oxyhotels.admin.feature_manage_hotel.domain.util.ActualRoom

@Entity(
    tableName = "hotelStorage"
)
data class HotelStorage(
    @PrimaryKey val _id: String = "",
    val hotelName: String = "",
    val hotelId: String = "",
    val phoneNo: String = "",
    val latitude: String = "",
    val longitude: String = "",
    val locationUrl: String = "",
    val hotelAddress: String = "",
    val hotelDescription: String = "",
    var refundPercentage: String = "0",
    val checkIn: String = "12-00",
    val checkOut: String = "11-00",
    val minPrice: Int = 0,
    val maxPrice: Int = 0,
    val imageData: MutableMap<String, MutableList<String>> = mutableMapOf(),
    val nearBy: MutableMap<String, MutableList<String>> = mutableMapOf(),
    val restrictions: MutableList<String> = mutableListOf(),
    val housePoliciesDos: MutableList<String> = mutableListOf(),
    val housePoliciesDonts: MutableList<String> = mutableListOf(),
    val houseAmenities: MutableList<String> = mutableListOf(),
    val roomTypes: MutableMap<String, MutableMap<String, Any>> = mutableMapOf(),
    val hotelStructure: List<List<ActualRoom>> = listOf()
)

