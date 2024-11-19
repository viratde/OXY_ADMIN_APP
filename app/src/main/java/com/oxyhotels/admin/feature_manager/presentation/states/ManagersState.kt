package com.oxyhotels.admin.feature_manager.presentation.states

import com.oxyhotels.admin.feature_manage_hotel.domain.model.HotelStorage
import com.oxyhotels.admin.feature_manager.domain.models.Manager

data class ManagersState(
    val managers: List<Manager> = listOf(),
    val isManagersLoading: Boolean = false,
    val isManagersLoaded: Boolean = false,

    val isManagerUpdating: Boolean = false,

    val isError: Boolean = false,
    val errorMessage: String = ""

)