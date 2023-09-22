package com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.states

data class AddRoomTypeState (
    val roomTypes : MutableList<RoomType>  = mutableListOf()
)


data class RoomType(
    val type: String,
    val availableRooms : Int,
    val features:MutableList<String> = mutableListOf()
)
