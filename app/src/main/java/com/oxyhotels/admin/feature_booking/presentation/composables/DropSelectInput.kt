package com.oxyhotels.admin.feature_booking.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DropSelectInput(
    value: String,
    placeholder: String,
    options: List<String>,
    onValueChange: (String) -> Unit
) {
    var isExpanded by remember {
        mutableStateOf(false)
    }
    Row(
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp,MaterialTheme.colorScheme.secondary, RoundedCornerShape(10.dp))
            .padding(horizontal = 10.dp, vertical = 18.dp)
    ) {
        Row(modifier = Modifier.width(120.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = placeholder,
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = MaterialTheme.colorScheme.secondary
                ),
                fontSize = 14.sp
            )
            Text(
                text = " - ",
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = MaterialTheme.colorScheme.secondary
                ),
                fontSize = 14.sp
            )
        }

        Column {
            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.secondary
                ),
                modifier = Modifier.clickable(onClick = { isExpanded = true })
            )
            DropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false },
                modifier = Modifier.background(Color.Black).weight(1f)
            ) {
                options.forEach {
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.secondary
                                ),
                                color = Color.White
                            )
                        },
                        onClick = { onValueChange(it);isExpanded = false }
                    )
                }
            }
        }
    }
}