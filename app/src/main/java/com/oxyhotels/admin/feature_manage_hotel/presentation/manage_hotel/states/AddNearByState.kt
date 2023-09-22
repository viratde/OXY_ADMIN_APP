package com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.states

data class AddNearByState (
    val nearBy:MutableList<NearByData> = mutableListOf()
        )

data class NearByData(
    val type: String,
    val locations:MutableList<String> = mutableListOf()
)