package com.oxyhotels.admin.feature_booking.presentation.composables

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompleteDatePicker(
    selectedDate: String,
    onDateChange: (String) -> Unit
) {

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Calendar.getInstance().timeInMillis
    )

    var isVisible by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(key1 = datePickerState.selectedDateMillis) {
        isVisible = !isVisible
        val date = datePickerState.selectedDateMillis?.let {
            millisToFormattedDate(
                datePickerState.selectedDateMillis!!,
                "Asia/Kolkata",
                "dd-MM-yyyy"
            )
        }
        if (date != null) {
            onDateChange(date)
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 10.dp)
            .clickable(onClick = { isVisible = !isVisible }),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = "Selected Date",
            style = MaterialTheme.typography.headlineSmall.copy(
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 20.sp
            )
        )

        Text(
            text = selectedDate,
            style = MaterialTheme.typography.headlineSmall.copy(
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 20.sp
            )
        )
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .offset { IntOffset(0, -300) }
            .animateContentSize()
            .height(if (isVisible) 500.dp else 0.dp)

    ) {
        DatePicker(
            state = datePickerState,
            title = {
            },
            headline = {
            },
            showModeToggle = false,
            dateFormatter = DatePickerFormatter()
        )
    }
}


fun millisToFormattedDate(millis: Long, zoneId: String, format: String): String {
    val date = Date(millis)
    val zone = TimeZone.getTimeZone(zoneId)
    val formatter = SimpleDateFormat(format, Locale.getDefault())
    formatter.timeZone = zone
    return formatter.format(date)
}
