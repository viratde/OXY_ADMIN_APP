package com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.viewmodels

import androidx.lifecycle.ViewModel
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.states.AddNearByState
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.states.NearByData
import kotlinx.coroutines.flow.MutableStateFlow

class AddNearByViewModel(
    addNearByState: AddNearByState
):ViewModel() {

    private val _state = MutableStateFlow(addNearByState)
    val state = _state

    fun addNewNearByType() {
        val pNearByData = state.value.nearBy.toMutableList()
        pNearByData.add(NearByData(type = ""))
        _state.value = state.value.copy(nearBy = pNearByData)
    }

    fun removeNearByType(index:Int){
        val pNearByData = state.value.nearBy.toMutableList()
        pNearByData.removeAt(index)
        _state.value = state.value.copy(nearBy = pNearByData)
    }

    fun updateNearByType(nearByData: NearByData, index: Int){
        val pNearByData = state.value.nearBy.toMutableList()
        pNearByData[index] = nearByData
        _state.value = state.value.copy(nearBy = pNearByData)
    }
}