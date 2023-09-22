package com.oxyhotels.admin.feature_analytics.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.oxyhotels.admin.common.composables.Screen
import com.oxyhotels.admin.feature_analytics.presentation.composables.DateTimePicker
import com.oxyhotels.admin.feature_analytics.presentation.viewmodels.AnalyticsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AnalyticsScreen(
    analyticsViewModel: AnalyticsViewModel
) {

    val state = analyticsViewModel.state.collectAsState()
    val life = LocalLifecycleOwner.current

    val snackBarHost = remember { SnackbarHostState() }

    LaunchedEffect(key1 = state.value.isError) {
        if (state.value.isError) {
            println("Error Launched")
            life.lifecycleScope.launch {
                snackBarHost.showSnackbar(state.value.errorMessage)
                analyticsViewModel.clearMessage()
            }
        }
    }

    LaunchedEffect(true) {
        life.lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                analyticsViewModel.loadAnalytics()
            }
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.secondary,
        snackbarHost = { SnackbarHost(hostState = snackBarHost) },
        modifier = Modifier
            .imePadding()
            .fillMaxSize()
    ) {
        Screen(
            padding = 15,
            verticalArrangement = Arrangement.Top
        ) {

            AnimatedVisibility(visible = state.value.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(40.dp)
                        .padding(8.dp),
                    color = Color.White
                )
            }

            DateTimePicker(
                selectedDate = state.value.startDate,
                placeHolder = "Start Date",
                onDateChange = {
                    analyticsViewModel.updateStartDate(it)
                }
            )

            DateTimePicker(
                selectedDate = state.value.endDate,
                placeHolder = "End Date",
                onDateChange = {
                    analyticsViewModel.updateEndDate(it)
                }
            )

            val verticalFields = state.value.analytics.keys.toList()

            val horizontalFields = listOf(
                "AR",
                "OR",
                "VR",
                "WS",
                "XS",
                "OS",
                "TS",
                "XU",
                "QCI",
                "CCI",
                "TCI",
                "QCO",
                "CCO",
                "TCO"
            )

            if (verticalFields.isNotEmpty()) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp, vertical = 10.dp)
                        .border(1.dp, MaterialTheme.colorScheme.secondary),
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.width(
                            IntrinsicSize.Max
                        )
                    ) {

                        Text(
                            text = "Hotel",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.secondary
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(1.dp, MaterialTheme.colorScheme.secondary)
                                .padding(horizontal = 10.dp, vertical = 10.dp)
                        )
                        verticalFields.map {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontSize = 14.sp,
                                    textAlign = TextAlign.Center,
                                    color = MaterialTheme.colorScheme.secondary
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(1.dp, MaterialTheme.colorScheme.secondary)
                                    .padding(horizontal = 10.dp, vertical = 10.dp)
                            )
                        }

                    }

                    LazyRow(
                        modifier = Modifier.border(1.dp, MaterialTheme.colorScheme.secondary)
                    ) {

                        items(horizontalFields.size) { h ->
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.width(
                                    IntrinsicSize.Max
                                )
                            ) {

                                Text(
                                    text = horizontalFields[h],
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontSize = 14.sp,
                                        textAlign = TextAlign.Center,
                                        color = MaterialTheme.colorScheme.secondary
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .border(1.dp, MaterialTheme.colorScheme.secondary)
                                        .padding(horizontal = 10.dp, vertical = 10.dp)
                                )

                                verticalFields.map {
                                    Text(
                                        text = state.value.analytics[it]?.get(horizontalFields[h])
                                            .toString(),
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontSize = 14.sp,
                                            textAlign = TextAlign.Center,
                                            color = MaterialTheme.colorScheme.secondary
                                        ),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .border(1.dp, MaterialTheme.colorScheme.secondary)
                                            .padding(horizontal = 10.dp, vertical = 10.dp)
                                    )
                                }
                            }
                        }

                    }
                }
            }
        }
    }

}