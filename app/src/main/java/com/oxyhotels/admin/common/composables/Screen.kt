package com.oxyhotels.admin.common.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Screen(
    modifier: Modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding()
        .imePadding(),
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    verticalArrangement: Arrangement.Vertical = Arrangement.Center,
    padding: Int = 20,
    isScrollable: Boolean = false,
    content: @Composable ColumnScope.() -> Unit,
) {

    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .navigationBarsPadding()
            .background(MaterialTheme.colorScheme.background)
            .padding(vertical = padding.dp)
            .let {



                if (isScrollable) {
                    return@let it.verticalScroll(scrollState)
                }

                it
            },
        horizontalAlignment = horizontalAlignment,
        verticalArrangement = verticalArrangement
    ) {
        content()
    }
}
