package com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.viewmodels

import androidx.lifecycle.ViewModel
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.states.AddBasicDetailState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class AddBasicDetailViewModel(
    addBasicDetailState: AddBasicDetailState
) : ViewModel() {

    private val _state = MutableStateFlow(addBasicDetailState)
    val state: MutableStateFlow<AddBasicDetailState> = _state


    fun setHotelName(name: String) {
        _state.update {
            state.value.copy(hotelName = name)
        }
    }

    fun toggleListedState() {
        _state.update {
            state.value.copy(
                isHotelListed = !state.value.isHotelListed
            )
        }
    }

    fun setTid(tid: String?) {
        _state.value = state.value.copy(
            tid = tid
        )
    }

    fun setHotelId(id: String) {
        _state.value = state.value.copy(hotelId = id)
    }

    fun setPhoneNumber(phoneNo: String) {
        _state.value = state.value.copy(phoneNo = phoneNo)
    }

    fun setMinimumPrice(minPrice: String) {
        if (minPrice.toIntOrNull() != null) {
            _state.value = state.value.copy(minPrice = minPrice.toInt())
        }
    }

    fun setMaximumPrice(maxPrice: String) {
        if (maxPrice.toIntOrNull() != null) {
            _state.value = state.value.copy(maxPrice = maxPrice.toInt())
        }
    }

    fun setHotelAddress(addr: String) {
        _state.value = state.value.copy(hotelAddress = addr)
    }

    fun setHotelDescription(description: String) {
        _state.value = state.value.copy(hotelDescription = description)
    }

    fun setLatitude(newValue: String) {
        _state.value = state.value.copy(latitude = newValue)
    }

    fun setLongitude(newValue: String) {
        _state.value = state.value.copy(longitude = newValue)
    }

    fun setLocationUrl(newValue: String) {
        _state.value = state.value.copy(locationUrl = newValue)
    }


    fun cancellationPolicy(refundPercent: String) {
        _state.value = state.value.copy(refundPercentage = refundPercent)
    }

    fun updateCheckInTime(time: String) {
        _state.value = state.value.copy(checkInTime = time)
    }

    fun updateCheckOut(time: String) {
        _state.value = state.value.copy(checkOutTime = time)
    }


}