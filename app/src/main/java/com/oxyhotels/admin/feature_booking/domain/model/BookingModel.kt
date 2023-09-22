package com.oxyhotels.admin.feature_booking.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey


data class BookedRoom(
    val roomType: String,
    val noOfRooms: Int,
    val noOfGuests: List<Int>
)

@Entity(tableName = "Bookings Storage")
data class BookingModel(

    val hotelId: String,
    @PrimaryKey val bookingId:Int,
    val propertyName:String,
    val checkInTime:String,
    val checkOutTime:String,
    val propertyAddress:String,
    val phone:String,
    val roomData:List<BookedRoom>,
    val price:Int,
    val totalPrice:Int,
    val name:String,
    val roomPhoto:String,
    val referenceId:String?,
    val hasCheckedIn:Boolean=false,
    val hasCheckedOut:Boolean=false,
    val hasNotShown:Boolean=false,
    val isCancelled:Boolean=false,
    val createdAt:String,
    val checkedOutDate:String?,
    val checkInDate:String?,
    val cancelledDate:String?,
    val noShowDate:String?,

    val isAdvancedPayment:Boolean=false,
    val advancedPaymentData:String?,
    val advancedPaymentMethod:String?,
    val isConvenience:Boolean=false,
    val convenienceData:String?,
    val bookingSource:String="Walking",
    val isCreatedByManager:Boolean=true,
    val extraPrices:List<ExtraPrices>,

    val accountingDates:List<String>,

    var pendingPayment:Int = 0
)

