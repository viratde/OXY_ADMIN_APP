package com.oxyhotels.admin.feature_booking.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.oxyhotels.admin.common.composables.Screen
import com.oxyhotels.admin.feature_booking.presentation.composables.FilterButton
import com.oxyhotels.admin.feature_booking.presentation.composables.FilterView
import com.oxyhotels.admin.feature_booking.presentation.composables.PendingBookingInfo
import com.oxyhotels.admin.feature_booking.presentation.viewmodels.PendingViewModel
import com.oxyhotels.admin.feature_booking.presentation.CreateBookingScreenRoute
import com.oxyhotels.admin.feature_booking.presentation.QrScannerScreenRoute

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PendingScreen(
    pendingViewModel: PendingViewModel,
    navController: NavController
) {

    val state = pendingViewModel.state.collectAsState()
    val snackBarHost = remember { SnackbarHostState() }

    LaunchedEffect(key1 = state.value.isError) {
        if (state.value.isError) {
            snackBarHost.showSnackbar(state.value.errorMessage)
            pendingViewModel.clearMessage()
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
            verticalArrangement = Arrangement.Top,
        ) {


            FilterView(navController = navController)

            AnimatedVisibility(visible = state.value.isDataLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(40.dp)
                        .padding(8.dp),
                    color = Color.White
                )
            }

            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                item {
                    if (state.value.bookings.isEmpty()) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(500.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "No Bookings",
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            )
                        }
                    }

                    Column {
                        state.value.bookings.map {
                            PendingBookingInfo(
                                bookingModel = it,
                                pendingViewModel = pendingViewModel,
                                state = state,
                                onClick = {
                                    pendingViewModel.startClearing(it.bookingId)
                                }
                            )
                        }
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                FilterButton(
                    value = "Scan QrCode",
                ) {
                    navController.navigate(QrScannerScreenRoute.route) {
                        launchSingleTop = true
                    }
                }

                FilterButton(
                    value = "Create Booking",
                ) {
                    navController.navigate(CreateBookingScreenRoute.route + "/hotelId=${pendingViewModel.hotelId}") {
                        launchSingleTop = true
                    }
                }
            }

        }
    }


}