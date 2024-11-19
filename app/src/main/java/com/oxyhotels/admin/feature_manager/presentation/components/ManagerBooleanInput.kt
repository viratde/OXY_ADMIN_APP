package com.oxyhotels.admin.feature_manager.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.components.HotelBooleanInput


@Composable
fun ManagerBooleanInput(
    value: Boolean,
    label: String,
    isEnabled: Boolean,
    onToggleValue: () -> Unit
) {

    HotelBooleanInput(
        value = value,
        title = label,
        backgroundColor = MaterialTheme.colorScheme.onBackground,
        textStyle = MaterialTheme.typography.headlineSmall.copy(
            fontSize = 14.sp,
            color = Color.White
        ),
        onValueChange = {
            onToggleValue()
        },
        isEnabled = isEnabled
    )

}