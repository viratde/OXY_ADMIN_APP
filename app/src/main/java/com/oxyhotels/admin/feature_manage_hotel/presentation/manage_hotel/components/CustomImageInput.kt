package com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.components

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.states.ImageData

@Composable
fun CustomImageInput(
    state: ImageData,
    categoryName: String,
    onDelete: () -> Unit,
    onStateChange: (ImageData) -> Unit,
    isEnabled: Boolean
){


    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                val newImages = state.images.toMutableList()
                newImages.add(uri.toString())
                onStateChange(state.copy(images = newImages))
            }
        }
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp),
    ) {
        Text(
            text = categoryName,
            style = MaterialTheme.typography.headlineSmall.copy(
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.secondary
            ),

        )
        HotelTextFieldInput(
            value = state.type,
            onValueChange = {onStateChange(state.copy(type = it))},
            textStyle = MaterialTheme.typography.headlineSmall.copy(color = Color.White, fontSize = 13.sp),
            placeHolderText = "Enter Category Name",
            backgroundColor = MaterialTheme.colorScheme.onBackground,
            isEnabled = isEnabled
        )

        val noOfVerticalGrids= ((state.images.size+1) /2 )+1
        Column(modifier = Modifier.fillMaxSize()) {
            for (i in 0 until noOfVerticalGrids){
                Row(
                    modifier = Modifier.fillMaxSize().padding(bottom = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    if(state.images.size > (2*i)) {
                        ShowPickedImage(uri = state.images[2 * i],isEnabled= isEnabled, onRemove = {onStateChange(state.copy(images = state.images.filter { uri -> uri != it }.toMutableList()))})
                    }
                    if(state.images.size > (2*i)+1) {
                        ShowPickedImage(uri = state.images[(2 * i)+1],isEnabled= isEnabled,  onRemove = {onStateChange(state.copy(images = state.images.filter { uri -> uri != it }.toMutableList()))})
                    }
                }
            }
        }
        if(isEnabled){
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                HotelButton(text = "Add Photo", isLoading = false) {
                    singlePhotoPickerLauncher.launch(PickVisualMediaRequest(mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly))
                }
                HotelButton(text = "Remove", isLoading = false) {
                    onDelete()
                }
            }
        }

    }
}