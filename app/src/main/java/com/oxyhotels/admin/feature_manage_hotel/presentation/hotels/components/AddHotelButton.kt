package com.oxyhotels.admin.feature_manage_hotel.presentation.hotels.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp


@Composable
fun AddHotelButton(
    backgroundColor:Color= MaterialTheme.colorScheme.background,
    foregroundColor:Color= MaterialTheme.colorScheme.secondary,
    shape: Shape = CircleShape,
    onClick:() -> Unit={}
){
    FloatingActionButton(
        onClick = { onClick() },
        shape = shape,
        containerColor = backgroundColor,
        contentColor = foregroundColor,
        elevation = FloatingActionButtonDefaults.elevation(2.dp)
    ) {
        Icon(imageVector = Icons.Default.Add, contentDescription = "Add Hotel Button Icons")
    }
}