package com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.states


data class AddImagesState(
    val imageData: MutableList<ImageData> = mutableListOf()
)

data class ImageData (
    val type:String,
    val images:MutableList<String> = mutableListOf()
)