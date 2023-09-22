package com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.viewmodels

import androidx.lifecycle.ViewModel
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.states.AddImagesState
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.states.ImageData
import kotlinx.coroutines.flow.MutableStateFlow

class AddImagesViewModel (
    addImagesState: AddImagesState
): ViewModel() {

    private val _state = MutableStateFlow(addImagesState)
    val state = _state

    fun addNewImageType() {
        val pImageData = state.value.imageData.toMutableList()
        pImageData.add(ImageData(type = ""))
        _state.value = state.value.copy(imageData = pImageData)
    }

    fun removeImageType(index:Int){
        val pImageData = state.value.imageData.toMutableList()
        pImageData.removeAt(index)
        _state.value = state.value.copy(imageData = pImageData)
    }

    fun updateImageType(imageData: ImageData, index: Int){
        val pImageData = state.value.imageData.toMutableList()
        pImageData[index]  = imageData
        _state.value = state.value.copy(imageData = pImageData)
    }

}