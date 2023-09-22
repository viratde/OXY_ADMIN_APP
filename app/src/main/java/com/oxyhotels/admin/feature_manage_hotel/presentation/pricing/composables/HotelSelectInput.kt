package com.oxyhotels.admin.feature_manage_hotel.presentation.pricing.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun HotelSelectInput(
    value: String,
    values: List<String>,
    onValueChange: (String) -> Unit,
    textStyle: TextStyle,
    placeHolderText: String,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    isEnabled: Boolean
) {

    var isExpanded by remember {
        mutableStateOf(false)
    }

    Row(
        modifier = modifier
            .clickable(onClick = {
                if (isEnabled) {
                    isExpanded = !isExpanded
                }
            })
            .background(backgroundColor)
            .padding(vertical = 14.dp, horizontal = 0.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(text = "Choose Room Type", style = textStyle, modifier = Modifier.width(130.dp))

            Row(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "  -  $value",
                    style = textStyle
                )

                DropdownMenu(expanded = isExpanded, onDismissRequest = {
                    isExpanded = false
                }) {

                    values.map {

                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = it,
                                    style = textStyle
                                )
                            },
                            onClick = { isExpanded = false; onValueChange(it) }
                        )

                    }

                }

            }
        }


    }
}
