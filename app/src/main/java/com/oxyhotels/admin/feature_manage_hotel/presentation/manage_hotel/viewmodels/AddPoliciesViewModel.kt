package com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.viewmodels

import androidx.lifecycle.ViewModel
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.states.AddPoliciesState
import kotlinx.coroutines.flow.MutableStateFlow

class AddPoliciesViewModel(
    addPoliciesState: AddPoliciesState
):ViewModel() {

    private val _state = MutableStateFlow(addPoliciesState)
    val state = _state


    fun updateRulesAndRestrictions(newValue: MutableList<String>) {
        _state.value = state.value.copy(restrictions = newValue)
    }



    fun updateDos(dos: MutableList<String>) {
        _state.value = state.value.copy(housePoliciesDos = dos)
    }

    fun updateDonts(donts: MutableList<String>) {
        _state.value = state.value.copy(housePoliciesDonts = donts)
    }

    fun updateAmenities(amenities: MutableList<String>) {
        _state.value = state.value.copy(houseAmenities = amenities)
    }
}