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
import androidx.navigation.NavHostController
import com.oxyhotels.admin.common.composables.Screen
import com.oxyhotels.admin.feature_booking.presentation.composables.BookingInfo
import com.oxyhotels.admin.feature_booking.presentation.composables.FilterButton
import com.oxyhotels.admin.feature_booking.presentation.composables.FilterView
import com.oxyhotels.admin.feature_booking.presentation.viewmodels.BookingStoreViewModel
import com.oxyhotels.admin.feature_booking.presentation.BookingScreenRoute
import com.oxyhotels.admin.feature_booking.presentation.CreateBookingScreenRoute
import com.oxyhotels.admin.feature_booking.presentation.QrScannerScreenRoute
import java.text.SimpleDateFormat
import java.util.Locale

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun InHouseScreen(
    navController: NavHostController,
    bookingStoreViewModel: BookingStoreViewModel,
) {

    val state = bookingStoreViewModel.state.collectAsState()
    val snackBarHost = remember { SnackbarHostState() }

    val format = SimpleDateFormat("dd-MM-YYYY HH:mm", Locale.getDefault())

    LaunchedEffect(key1 = state.value.isError) {
        if (state.value.isError) {
            snackBarHost.showSnackbar(state.value.errorMessage)
            bookingStoreViewModel.clearMessage()
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

            AnimatedVisibility(visible = state.value.isInHouseLoading) {
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
                    if(state.value.inHouseBookings.isEmpty()){
                        Column(
                            modifier =  Modifier.fillMaxWidth().height(500.dp),
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
                        state.value.inHouseBookings.map {
                            BookingInfo(
                                bookingModel = it,
                                onClick = {
                                    navController.navigate("${BookingScreenRoute.route}?id=${it.bookingId}") {
                                        launchSingleTop = true
                                    }
                                }
                            )
                        }
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                FilterButton(
                    value = "Scan QrCode",
                ) {
                    navController.navigate(QrScannerScreenRoute.route){
                        launchSingleTop = true
                    }
                }

                FilterButton(
                    value = "Create Booking",
                ) {
                    navController.navigate(CreateBookingScreenRoute.route+"/hotelId=${bookingStoreViewModel.hotelId}") {
                        launchSingleTop = true
                    }
                }
            }
        }
    }

}

