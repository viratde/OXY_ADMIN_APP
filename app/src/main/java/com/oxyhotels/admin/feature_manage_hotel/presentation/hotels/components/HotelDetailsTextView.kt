package com.oxyhotels.admin.feature_manage_hotel.presentation.hotels.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HotelDetailsTextView(
    categoryName: String,
    categoryValue: String
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
//        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = categoryName,
            style = MaterialTheme.typography.headlineSmall.copy(
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.secondary
            ),
            modifier = Modifier.width(120.dp)
        )

        Text(
            text = "-   $categoryValue",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.secondary
            ),
            modifier = Modifier.weight(1f),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }


}