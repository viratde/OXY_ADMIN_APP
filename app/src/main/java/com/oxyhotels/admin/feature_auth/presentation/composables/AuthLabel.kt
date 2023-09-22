package com.oxyhotels.admin.feature_auth.presentation.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AuthLabel(
    text:String
){

    Text(
        text = text,
        style = MaterialTheme.typography.headlineMedium.copy(
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 15.sp
        ),
        modifier = Modifier
            .padding(vertical = 5.dp, horizontal = 28.dp)
            .widthIn(max = 350.dp, min = 320.dp)
    )

}