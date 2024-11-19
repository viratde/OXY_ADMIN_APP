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
    val latitude: Double,
    val longitude: Double,
    val locationUrl: String = "",
    val hotelAddress: String = "",
    val hotelDescription: String = "",
    var refundPercentage: Int,
    var isHotelListed: Boolean = false,
    val checkIn: String = "12-00",
    val checkOut: String = "11-00",
    val minPrice: Int = 0,
    val maxPrice: Int = 0,
    val imageData: Map<String, List<String>> = mapOf(),
    val nearBy: Map<String, List<String>> = mapOf(),
    val restrictions: List<String> = listOf(),
    val housePoliciesDos: List<String> = listOf(),
    val housePoliciesDonts: List<String> = listOf(),
    val houseAmenities: List<String> = listOf(),
    val roomTypes: Map<String, Map<String, Any>> = mapOf(),
    val hotelStructure: Map<String, List<ActualRoom>> = mapOf(),
    val tid: String? = null,
)

