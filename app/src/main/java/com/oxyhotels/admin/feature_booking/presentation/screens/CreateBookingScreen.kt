package com.oxyhotels.admin.feature_booking.presentation.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.oxyhotels.admin.common.composables.Screen
import com.oxyhotels.admin.feature_auth.presentation.composables.AuthButton
import com.oxyhotels.admin.feature_booking.presentation.composables.AdvancedPaymentWithMethodInput
import com.oxyhotels.admin.feature_booking.presentation.composables.CheckInCheckOutView
import com.oxyhotels.admin.feature_booking.presentation.composables.CustomBookRoom
import com.oxyhotels.admin.feature_booking.presentation.composables.DropNumberInput
import com.oxyhotels.admin.feature_booking.presentation.composables.DropSelectInput
import com.oxyhotels.admin.feature_booking.presentation.composables.DropStringInput
import com.oxyhotels.admin.feature_booking.presentation.composables.DropTickAndNumberInput
import com.oxyhotels.admin.feature_booking.presentation.viewmodels.CreateBookingViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateBookingScreen(
    navController: NavController,
    createBookingViewModel: CreateBookingViewModel
) {
    val life = LocalLifecycleOwner.current
    val context = LocalContext.current

    fun showMessage(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        if(msg == "Booking Created Successfully."){
            navController.popBackStack()
        }
    }


    val state = createBookingViewModel.state.collectAsState()
    LaunchedEffect(key1 = state.value.isError) {
        if (state.value.isError) {
            showMessage(state.value.errorMessage)
            createBookingViewModel.clearMessage()

        }
    }

    val currentTimeInMillis = remember {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        calendar.timeInMillis
    }

    val dateState = rememberDateRangePickerState(
        initialSelectedStartDateMillis = remember {
            Calendar.getInstance().timeInMillis
        },
        initialSelectedEndDateMillis = remember {
            val calendar = Calendar.getInstance().apply {
                add(Calendar.DAY_OF_MONTH, 1)
            }
            calendar.timeInMillis
        },
        yearRange = 2023..2023,
    )

    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    LaunchedEffect(key1 = dateState.selectedEndDateMillis) {
        createBookingViewModel.updateCheckInTime(dateState.selectedStartDateMillis?.let { Date(it) }
            ?.let { dateFormat.format(it) } ?: "")
    }
    LaunchedEffect(key1 = dateState.selectedEndDateMillis) {
        createBookingViewModel.updateCheckOutTime(dateState.selectedEndDateMillis?.let { Date(it) }
            ?.let { dateFormat.format(it) } ?: "")
    }
    val snackBarHost = remember { SnackbarHostState() }



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
            isScrollable = true,
            verticalArrangement = Arrangement.Top,
        ) {

            Column(
                modifier = Modifier
                    .padding(horizontal = 10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                DropNumberInput(
                    placeholder = "Phone",
                    value = state.value.booking.phone,
                    onValueChange = {
                        createBookingViewModel.updatePhone(it)
                    }
                )


                DropStringInput(
                    placeholder = "Name",
                    value = state.value.booking.name,
                    onValueChange = {
                        createBookingViewModel.updateName(it)
                    }
                )

                DropStringInput(
                    placeholder = "Email",
                    value = state.value.booking.email,
                    onValueChange = {
                        createBookingViewModel.updateEmail(it)
                    }
                )


                DropStringInput(
                    placeholder = "ReferenceId",
                    value = state.value.booking.referenceId,
                    onValueChange = {
                        createBookingViewModel.updateReferenceId(it)
                    }
                )

                DropNumberInput(
                    placeholder = "Price",
                    value = state.value.booking.price,
                    onValueChange = {
                        createBookingViewModel.updatePrice(it)
                    }
                )


                DropSelectInput(
                    value = state.value.booking.bookingSource,
                    placeholder = "Source",
                    options = listOf("Walking", "Oyo"),
                    onValueChange = {
                        createBookingViewModel.updateBookingSource(it)
                    }
                )

                if (state.value.booking.referenceId.isNotEmpty()) {
                    DropTickAndNumberInput(
                        placeholder = "Convenience",
                        value = state.value.booking.convenienceData,
                        isOpen = state.value.booking.isConvenience,
                        onActiveChange = {
                            createBookingViewModel.updateIsConvenience(it)
                        }
                    ) {
                        createBookingViewModel.updateConvenienceData(it)
                    }
                }
                AdvancedPaymentWithMethodInput(
                    placeholder = "Advanced Payment",
                    value1 = state.value.booking.advancedPaymentMethod,
                    value2 = state.value.booking.advancedPaymentData,
                    isOpen = state.value.booking.isAdvancedPayment,
                    onActiveChange = { createBookingViewModel.updateIsAdvancedPayment(it) },
                    onValue1Change = { createBookingViewModel.updateAdvancedPaymentMethod(it) },
                    onValue2Change = { createBookingViewModel.updateAdvancedPaymentData(it) },
                )

                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    state.value.booking.rooms.map {

                        Column(
                            modifier = Modifier
                                .padding(vertical = 5.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .border(
                                    1.dp,
                                    MaterialTheme.colorScheme.secondary,
                                    RoundedCornerShape(10.dp)
                                ).padding(horizontal = 10.dp),
                        ) {

                            CustomBookRoom(
                                isEditable = true,
                                mainText = it.roomType,
                                onClick = { action ->
                                    createBookingViewModel.updateRoomsValue(
                                        it.roomType,
                                        action
                                    )
                                },
                                value = it.noOfRooms.toString()
                            )

                            it.noOfGuests.mapIndexed { index, value ->
                                CustomBookRoom(
                                    isEditable = true,
                                    mainText = "Guests in Room${index + 1}",
                                    onClick = { action ->
                                        createBookingViewModel.updateGuest(
                                            it.roomType,
                                            action,
                                            index
                                        )
                                    },
                                    value = value.toString()
                                )
                            }
                        }

                    }
                }
            }


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(520.dp)
            ) {
                DateRangePicker(
                    state = dateState,
                    headline = {
                        CheckInCheckOutView(
                            checkInTime = state.value.booking.checkInTime,
                            checkOutTime = state.value.booking.checkOutTime
                        )
                    },
                    showModeToggle = false,
                    title = {},
                    dateValidator = {
                        return@DateRangePicker it > currentTimeInMillis
                    },
                    dateFormatter = DatePickerFormatter("yy MM dd", "yy MM dd", "yy MM dd"),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colorScheme.onBackground)
                )
            }


            AuthButton(
                text = "Create Booking",
                isLoading = state.value.isLoading
            ) {
              if(!state.value.isLoading){
                  life.lifecycleScope.launch {
                      withContext(Dispatchers.IO) {
                          createBookingViewModel.createBooking()
                      }
                  }
              }
            }

        }

    }

}

