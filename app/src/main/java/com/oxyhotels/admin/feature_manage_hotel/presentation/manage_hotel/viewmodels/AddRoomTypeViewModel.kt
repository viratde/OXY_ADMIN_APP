package com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.viewmodels

import androidx.lifecycle.ViewModel
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.states.AddRoomTypeState
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.states.RoomType
import kotlinx.coroutines.flow.MutableStateFlow

class AddRoomTypeViewModel(
    addRoomTypeState: AddRoomTypeState
):ViewModel() {

    private val _state = MutableStateFlow(addRoomTypeState)
    val state = _state


    fun addNewRoomType() {
        val pRoomTypes = state.value.roomTypes.toMutableList()
        pRoomTypes.add(RoomType(type = "", availableRooms = 0, features = mutableListOf()))
        _state.value = state.value.copy(roomTypes = pRoomTypes)
    }

    fun removeRoomType(index:Int){
        val pRoomTypes = state.value.roomTypes.toMutableList()
        pRoomTypes.removeAt(index)
        _state.value = state.value.copy(roomTypes = pRoomTypes)
    }

    fun updateRoomType(roomType: RoomType, index: Int){
        val pRoomTypes = state.value.roomTypes.toMutableList()
        pRoomTypes[index] = roomType
        _state.value = state.value.copy(roomTypes = pRoomTypes)
    }
}