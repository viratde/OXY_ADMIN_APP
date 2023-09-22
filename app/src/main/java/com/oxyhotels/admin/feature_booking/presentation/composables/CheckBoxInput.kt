package com.oxyhotels.admin.feature_booking.presentation.composables

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


@Composable
fun CheckBoxesInput(
    values:List<String>,
    selectedValues:List<String>,
    onValueChange:(List<String>) -> Unit
){

    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, MaterialTheme.colorScheme.secondary, RoundedCornerShape(10.dp))
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {

        Text(
            text = "Dates",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.secondary
            ),
            modifier = Modifier.padding(vertical = 10.dp)
        )
        values.map {
            CheckBoxInput(
                placeholder = it,
                isOpen = selectedValues.contains(it),
                onActiveChange = {action ->
                    if(action){
                        val list = selectedValues.toMutableList()
                        list.add(it)
                        onValueChange(list.toList())
                    }else{
                        val list = selectedValues.toMutableList()
                        list.remove(it)
                        onValueChange(list.toList())
                    }
                }
            )
        }

    }
}

@Composable
fun CheckBoxInput(
    placeholder:String,
    isOpen:Boolean,
    onActiveChange:(Boolean) -> Unit
){
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
}