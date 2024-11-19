package com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun HotelBooleanInput(
    value: Boolean,
    title: String,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    textStyle: TextStyle,
    isEnabled:Boolean,
    onValueChange: (Boolean) -> Unit,
) {

    Row(
        modifier = modifier
            .background(backgroundColor)
            .padding(vertical = 14.dp, horizontal = 0.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Checkbox(
                checked = value,
                onCheckedChange = {
                    onValueChange(it)
                },
                enabled = isEnabled
            )

            Text(
                text = title,
                style = textStyle
            )

        }
    }

}