package com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.states

import com.oxyhotels.admin.feature_manage_hotel.domain.util.ActualRoom

data class AddHotelStructureState(
    val structure: List<List<ActualRoom>>
)