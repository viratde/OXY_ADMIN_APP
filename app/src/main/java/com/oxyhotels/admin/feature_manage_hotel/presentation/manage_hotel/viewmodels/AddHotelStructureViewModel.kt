package com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.viewmodels

import androidx.lifecycle.ViewModel
import com.oxyhotels.admin.feature_manage_hotel.domain.util.ActualRoom
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.states.AddHotelStructureState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class AddHotelStructureViewModel(
    addHotelStructureState: AddHotelStructureState
) : ViewModel() {

    private val _state = MutableStateFlow(addHotelStructureState)
    val state = _state

    fun addNewFloor() {
        println("state updated ${state.value.structure}")
        _state.update {
            state.value.copy(
                structure = (it.structure + listOf(listOf()))
            )
        }
    }

    fun removeFloor(index: Int) {
        _state.update {
            val oldStructure = it.structure.toMutableList()
            oldStructure.removeAt(index)
            state.value.copy(
                structure = oldStructure
            )
        }
    }

    fun addRoom(index: Int,roomType: String) {
        _state.update {
            val oldStructure = it.structure.toMutableList()
            val floorData = oldStructure[index].toMutableList()
            floorData.add(
                ActualRoom(
                    roomNo = "${((index + 1) * 100) + floorData.size + 1}",
                    roomType = roomType
                )
            )
            oldStructure[index] = floorData
            state.value.copy(
                structure = oldStructure
            )
        }
    }

    fun updateRoom(index: Int, rIndex: Int, roomName: String, roomType: String) {
        _state.update {
            val oldStructure = it.structure.toMutableList()
            val floorData = oldStructure[index].toMutableList()
            floorData[rIndex] = floorData[rIndex].copy(
                roomNo = roomName,
                roomType = roomType
            )
            oldStructure[index] = floorData
            state.value.copy(
                structure = oldStructure
            )
        }
    }


    fun removeRoom(index: Int, rIndex: Int) {
        _state.update {
            val oldStructure = it.structure.toMutableList()
            val floorData = oldStructure[index].toMutableList()
            floorData.removeAt(rIndex)
            oldStructure[index] = floorData
            state.value.copy(
                structure = oldStructure
            )
        }
    }


}