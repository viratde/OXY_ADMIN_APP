package com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AddHotelHeader(
    text:String
){

    Text(
        text = text,
        color = MaterialTheme.colorScheme.secondary,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.headlineSmall.copy(
            fontSize = 23.sp
        ),
        modifier = Modifier.fillMaxWidth().padding(
            vertical = 10.dp
        )
    )

}
