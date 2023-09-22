package com.oxyhotels.admin.feature_booking.presentation.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DropTickAndNumberInput(
    placeholder: String,
    value: String,
    isOpen: Boolean,
    onActiveChange: (Boolean) -> Unit,
    onValueChange: (String) -> Unit
) {

    Row(
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp,MaterialTheme.colorScheme.secondary, RoundedCornerShape(10.dp))
            .padding(horizontal = 10.dp, vertical = if(isOpen) 2.dp else 15.dp)
    ) {
        Row(
            modifier = Modifier.width(130.dp),
            horizontalArrangement = Arrangement.spacedBy(3.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isOpen,
                onCheckedChange = { onActiveChange(it) },
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.secondary,
                    checkmarkColor = MaterialTheme.colorScheme.background
                ),
                modifier = Modifier
                    .width(25.dp)
                    .height(25.dp)
                    .padding(end = 3.dp)
            )
            Text(
                text = placeholder,
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = MaterialTheme.colorScheme.secondary
                ),
                fontSize = 14.sp
            )

        }
        AnimatedVisibility(visible = isOpen) {
            if (isOpen) {

                BasicTextField(
                    value = value.ifEmpty { "0" },
                    onValueChange = {
                                    if(it.toIntOrNull() != null){
                                        onValueChange(it.toInt().toString())
                                    }else{
                                        onValueChange("0")
                                    }
                    },
                    singleLine = true,
                    textStyle = MaterialTheme.typography.headlineSmall.copy(
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.secondary
                    ),
                    cursorBrush = Brush.linearGradient(
                        listOf(
                            MaterialTheme.colorScheme.secondary,
                            MaterialTheme.colorScheme.secondary
                        )
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    ),
                    modifier = Modifier
                        .padding(vertical = 2.dp)
                        .height(50.dp),
                    decorationBox = {
                        Row(
                            modifier = Modifier.weight(1f),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            it()
                        }
                    }
                )

            }
        }
    }
}