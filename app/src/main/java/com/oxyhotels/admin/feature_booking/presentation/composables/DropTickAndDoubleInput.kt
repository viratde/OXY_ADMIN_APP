package com.oxyhotels.admin.feature_booking.presentation.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oxyhotels.admin.feature_booking.presentation.composables.DropNumberInput

@Composable
fun DropTickAndDoubleInput(
    placeholder: String,
    value1: String,
    value2: String,
    isOpen: Boolean,
    onActiveChange: (Boolean) -> Unit,
    onValue1Change: (String) -> Unit,
    onValue2Change: (String) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, MaterialTheme.colorScheme.secondary, RoundedCornerShape(10.dp))
            .padding(horizontal = 10.dp, vertical = 14.dp)
    ) {
        Row(
            modifier = Modifier.width(200.dp),
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
                Column(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(9.dp)
                ) {
                    DropNumberInput(
                        placeholder = "No Of Persons",
                        value = value1,
                        onValueChange = { onValue1Change(it) }
                    )
                    DropNumberInput(
                        placeholder = "Charge",
                        value = value2,
                        onValueChange = { onValue2Change(it) }
                    )
                }
            }
        }
    }
}