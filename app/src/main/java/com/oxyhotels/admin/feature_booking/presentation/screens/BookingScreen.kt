package com.oxyhotels.admin.feature_booking.presentation.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import com.oxyhotels.admin.common.composables.Screen
import com.oxyhotels.admin.feature_auth.presentation.composables.AuthButton
import com.oxyhotels.admin.feature_booking.presentation.composables.BookingRoomData
import com.oxyhotels.admin.feature_booking.presentation.composables.ExtraPaymentInput
import com.oxyhotels.admin.feature_booking.presentation.composables.PairText
import com.oxyhotels.admin.feature_booking.presentation.composables.updateInInput
import com.oxyhotels.admin.feature_booking.presentation.viewmodels.BookingViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BookingScreen(
    bookingViewModel: BookingViewModel
) {

    val state = bookingViewModel.state.collectAsState()
    val life = LocalLifecycleOwner.current

    val context = LocalContext.current
    fun showMessage(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }
    LaunchedEffect(key1 = state.value.isError) {
        if (state.value.isError) {
            showMessage(state.value.errorMessage)
            bookingViewModel.clearMessage()
        }
    }

    Screen(
        padding = 15,
        isScrollable = true,
        verticalArrangement = Arrangement.Top,
    ) {


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.onBackground)
                .padding(10.dp)
                .animateContentSize()
        ) {
            Column() {
                Column(
                    modifier = Modifier
                        .padding(start = 0.dp, end = 10.dp, top = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {

                    state.value.bookingModel.let {

                        PairText(
                            propertyName = "Hotel",
                            propertyValue = it.propertyName,
                            fontSize = 16
                        )
                        PairText(
                            propertyName = "Name",
                            propertyValue = it.name
                        )
                        PairText(
                            propertyName = "CheckInTime",
                            propertyValue = it.checkInTime
                        )
                        PairText(
                            propertyName = "CheckOutTime",
                            propertyValue = it.checkOutTime
                        )
                        PairText(
                            propertyName = "Booking Id",
                            propertyValue = "OXY${it.bookingId}"
                        )
                        PairText(
                            propertyName = "Booking Date",
                            propertyValue = it.createdAt
                        )
                        PairText(
                            propertyName = "Booking Amount",
                            propertyValue = it.price.toString()
                        )

                        it.extraPrices.map {ePrice ->
                            PairText(
                                propertyName = ePrice.chargeType,
                                propertyValue = ePrice.amount.toString()
                            )
                        }


                        PairText(
                            propertyName = "Total Amount",
                            propertyValue = it.totalPrice.toString()
                        )

                        BookingRoomData(rooms = it.roomData)

                    }
                }
                updateInInput(state = state, bookingViewModel = bookingViewModel)


                ExtraPaymentInput(bookingViewModel = bookingViewModel, state = state)

                Column {
                    println(state.value.bookingModel.checkInDate)
                    if (!state.value.bookingModel.isCancelled && !state.value.bookingModel.hasNotShown && state.value.bookingModel.hasCheckedIn && state.value.bookingModel.checkInDate == null) {
                        if (!state.value.isCheckingInActive) {
                            AuthButton(
                                text = "Update",
                                isLoading = state.value.isCheckingIn
                            ) {
                                bookingViewModel.startCheckIn()
                            }
                        }
                    } else if(!state.value.bookingModel.hasCheckedOut && state.value.bookingModel.hasCheckedIn){
                        if(!state.value.isCheckingOutActive){
                            AuthButton(
                                text = "Extra Price",
                                isLoading = state.value.isCheckingOutOtpSending
                            ) {
                                bookingViewModel.startExtraPriceUpdate()
                            }
                        }
                    }
                }
            }
        }
    }
}


//@Composable
//fun BookingScreen(
//    bookingViewModel:BookingViewModel,
//) {
//
//    val context = LocalContext.current;
//    val life = LocalLifecycleOwner.current;
//
//
//    fun showMessage(msg: String) {
//        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
//    }
//
//    val scrollState = rememberScrollState()
//
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(10.dp)
//            .clip(RoundedCornerShape(10.dp))
//            .background(MaterialTheme.colorScheme.onBackground)
//            .padding(10.dp)
//            .animateContentSize()
//            .verticalScroll(scrollState)
//    ) {
//        Row(
//            verticalAlignment = Alignment.Top,
//        ) {
//            Column() {
//                Image(
//                    painter = painterResource(id = R.drawable.hotel),
//                    contentDescription = "",
//                    modifier = Modifier
//                        .widthIn(150.dp, 160.dp)
//                        .clip(RoundedCornerShape(10.dp)),
//                )
//                Column(
//                    modifier = Modifier
//                        .padding(start = 0.dp, end = 10.dp, top = 10.dp)
//                        .widthIn(150.dp, 160.dp)
//                ) {
//                    Text(
//                        text = "Name - ${booking.name}",
//                        style = MaterialTheme.typography.h4,
//                    )
//                    Text(
//                        text = "${booking.checkIn.slice(8..9)}-${booking.checkIn.slice(5..6)}" + " - " + "${
//                            booking.checkOut.slice(
//                                8..9
//                            )
//                        }-${booking.checkOut.slice(5..6)}",
//                        style = MaterialTheme.typography.h4,
//                    )
////                    Text(text = "Date - ${}")
//                }
//            }
//            Column(modifier = Modifier.padding(start = 15.dp, end = 10.dp, bottom = 10.dp)) {
//                Text(
//                    text = booking.propertyName,
//                    style = MaterialTheme.typography.h3
//                )
//                Text(
//                    text = "Room - ${booking.noOfRooms}",
//                    style = MaterialTheme.typography.h4,
//                )
//                Text(
//                    text = "Guests - ${booking.guestsInEachRoom}",
//                    style = MaterialTheme.typography.h4,
//                )
//                Text(
//                    text = "Price - ₹${booking.totalPrice}",
//                    style = MaterialTheme.typography.h4
//                )
//                Text(
//                    text = "Id - OXY${booking.bookingId}",
//                    style = MaterialTheme.typography.h4,
//                )
//                if (!booking.referenceId.isNullOrEmpty()) {
//                    Text(
//                        text = "OYOID - ${booking.referenceId}",
//                        style = MaterialTheme.typography.h4,
//                    )
//                }
//                if (booking.isCancelled) {
//                    Text(
//                        text = "Status - Cancelled",
//                        style = MaterialTheme.typography.h4,
//                    )
//                }
//                if (booking.hasNotShown) {
//                    Text(
//                        text = "Status - No Shown",
//                        style = MaterialTheme.typography.h4,
//                    )
//                }
//            }
//        }
//        Column(modifier = Modifier.padding(start = 5.dp, end = 10.dp, bottom = 10.dp)) {
//            Text(
//                text = "Booking Date - ${booking.createdAt}",
//                style = MaterialTheme.typography.h4,
//            )
//            if (booking.checkInDate != "null") {
//                Text(
//                    text = "CheckIn Date - ${booking.checkInDate}",
//                    style = MaterialTheme.typography.h4,
//                )
//            }
//            if (booking.checkedOutDate != "null") {
//                Text(
//                    text = "CheckOut Date - ${booking.checkedOutDate}",
//                    style = MaterialTheme.typography.h4,
//                )
//            }
//
//        }
//
//        if (isCheckInOpen) {
//            Column(modifier = Modifier.fillMaxWidth()) {
//
//
//                DropDownInput(
//                    value = noOfFloors,
//                    onValueChange = { noOfFloors = it },
//                    placeholder = "Floor No"
//                )
//
//                DropDownInput(
//                    value = noOfRooms,
//                    onValueChange = { noOfRooms = it },
//                    placeholder = "Room No",
//                )
//
//                DropDownInput(
//                    value = phone,
//                    onValueChange = { phone = it },
//                    placeholder = "Register No",
//                )
//
//                if (booking.referenceId != null && booking.referenceId.isNotEmpty()) {
//                    DropDownInput(
//                        placeholder = "Ota",
//                        value = oyo,
//                        onValueChange = { oyo = it },
//                    )
//                }
//
//                DropDownInput(placeholder = "Cash",
//                    value = cash,
//                    onValueChange = { cash = it }
//                )
//
//                DropDownInput(placeholder = "Bank",
//                    value = bank,
//                    onValueChange = { bank = it }
//                )
//
//                DropDownInput(placeholder = "Pending",
//                    value = pending,
//                    onValueChange = { pending = it }
//                )
//
//                DropTickAndNumberInput(
//                    placeholder = "Early CheckIn",
//                    value = earlyCheckInData,
//                    isOpen = isEarlyCheckIn,
//                    onActiveChange = {
//                        isEarlyCheckIn = it; if (!it) {
//                        earlyCheckInData = "0"
//                    }
//                    }
//                ) {
//                    earlyCheckInData = it
//                }
//
//                DropTickAndNumberInput(
//                    placeholder = "Extra Charge",
//                    value = extraChargeData,
//                    isOpen = isExtraCharge,
//                    onActiveChange = {
//                        isExtraCharge = it; if (!it) {
//                        extraChargeData = "0"
//                    }
//                    }
//                ) {
//                    extraChargeData = it
//                }
//
//                DropTickAndNumberInput(
//                    placeholder = "Meal Price",
//                    value = mealPriceData,
//                    isOpen = isMealPrice,
//                    onActiveChange = {
//                        isMealPrice = it; if (!it) {
//                        mealPriceData = "0"
//                    }
//                    }
//                ) {
//                    mealPriceData = it
//                }
//
//                DropTickAndDoubleInput(
//                    placeholder = "Extra Person",
//                    value1 = noOfExtraPersons,
//                    value2 = chargeOfExtraPerson,
//                    isOpen = isExtraPerson,
//                    onActiveChange = {
//                        isExtraPerson = it;if (!it) {
//                        noOfExtraPersons = "0";chargeOfExtraPerson = "0";
//                    }
//                    },
//                    onValue1Change = { noOfExtraPersons = it }
//                ) {
//                    chargeOfExtraPerson = it
//                }
//
//                DropTickAndNumberInput(
//                    placeholder = "Late Checkout",
//                    value = lateCheckOutData,
//                    isOpen = isLateCheckOut,
//                    onActiveChange = {
//                        isLateCheckOut = it; if (!it) {
//                        lateCheckOutData = "0"
//                    }
//                    }
//                ) {
//                    lateCheckOutData = it
//                }
//
//            }
//
//
//        }
//
//
//        if (isCheckOutOpen) {
//
//            Column(modifier = Modifier.fillMaxWidth()) {
//                DropDownInput(placeholder = "Otp",
//                    value = checkOutPhone,
//                    onValueChange = { checkOutPhone = it }
//                )
//                if (!booking.isLateCheckOut) {
//                    AdvancedPaymentWithMethodInput(
//                        placeholder = "Late Check Out",
//                        value1 = lateCheckOutPaymentMethod,
//                        value2 = lateCheckOutData,
//                        isOpen = isLateCheckOut,
//                        onActiveChange = {
//                            isLateCheckOut = it; if (!it) {
//                            lateCheckOutData = "0"
//                        }
//                        },
//                        onValue1Change = { lateCheckOutPaymentMethod = it },
//                        onValue2Change = { lateCheckOutData = it }
//                    )
//                }
//            }
//        }
//        if (isAction && !booking.hasNotShown && !booking.isCancelled) {
//            if (!booking.hasNotShown) {
//                Row(
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 10.dp, vertical = 5.dp)
//                ) {
//                    Button(
//                        onClick = {
//                            if (isCheckOutOpen) {
//                                isCheckOutOpen = false
//                            } else {
//                                if (!hasCheckedIn) {
//                                    if (!isCheckInOpen) {
//                                        if (todaydate != booking.checkIn && pDate != booking.checkIn) {
//                                            showMessage("This Booking Cannot be Checked In Today.");
//                                        } else {
//                                            isCheckInOpen = true
//                                        }
//                                    } else if (!isCheckingIn) {
//                                        life.lifecycleScope.launch {
//                                            checkIn()
//                                        }
//                                    }
//                                }
//                            }
//                        },
//                        modifier = Modifier
//                            .width(130.dp)
//                            .clip(RoundedCornerShape(20.dp)),
//                        colors = ButtonDefaults.buttonColors(
//                            contentColor = backgroundColor,
//                            backgroundColor = Color(0xFF0B3B2D)
//                        ),
//                    ) {
//                        Text(
//                            text = if (isCheckOutOpen) {
//                                "Cancel"
//                            } else {
//                                if (hasCheckedIn) {
//                                    "Checked In"
//                                } else {
//                                    if (isCheckInOpen && isCheckingIn) {
//                                        "Checking In"
//                                    } else if (isCheckInOpen) {
//                                        "Confirm"
//                                    } else {
//                                        "Check In"
//                                    }
//                                }
//                            },
//                            style = MaterialTheme.typography.h4,
//                            fontSize = 15.sp,
//                            modifier = Modifier.clip(RoundedCornerShape(20.dp)),
//                        )
//                    }
//
//                    Button(
//                        onClick = {
//
//                            if (isCheckInOpen && !isCheckingIn) {
//                                isCheckInOpen = false
//                            } else {
//                                if (!hasCheckedOut) {
//                                    if (!hasCheckedIn) {
//                                        showMessage("User has not Checked in");
//                                    } else if (!isCheckingOut) {
//                                        if (!isOtpSent) {
//                                            println("sending out")
//                                            life.lifecycleScope.launch {
//                                                sendOtp()
//                                            }
//                                        } else if (!isOtpVerified) {
//                                            life.lifecycleScope.launch {
//                                                verifyOtp()
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//
//                        },
//                        modifier = Modifier
//                            .width(130.dp)
//                            .clip(RoundedCornerShape(20.dp)),
//                        colors = ButtonDefaults.buttonColors(
//                            contentColor = backgroundColor,
//                            backgroundColor = Color(0xFF0B3B2D)
//                        ),
//                    ) {
//                        Text(
//                            text = if (isCheckInOpen) {
//                                "Cancel"
//                            } else {
//                                if (hasCheckedOut) {
//                                    "Checked Out"
//                                } else {
//                                    if (isOtpSending) {
//                                        "Sending Otp"
//                                    } else if (isOtpVerifying) {
//                                        "Verifying Otp"
//                                    } else if (isCheckOutOpen && isCheckingOut) {
//                                        "Checking Out"
//                                    } else if (isOtpSent) {
//                                        "Verify"
//                                    } else if (isCheckOutOpen) {
//                                        "Confirm"
//                                    } else {
//                                        "Check Out"
//                                    }
//                                }
//                            },
//                            style = MaterialTheme.typography.h4,
//                            fontSize = 15.sp,
//                            modifier = Modifier.clip(RoundedCornerShape(20.dp))
//                        )
//                    }
//
//                }
//            }
//            if (!booking.hasCheckedIn && !booking.hasNotShown) {
//                Row(
//                    horizontalArrangement = Arrangement.SpaceAround,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 10.dp, vertical = 5.dp)
//                ) {
//                    Button(
//                        onClick = {
//                            if (!isNoShowing) {
//                                life.lifecycleScope.launch {
//                                    noShow()
//                                }
//                            }
//                        },
//                        modifier = Modifier
//                            .width(130.dp)
//                            .clip(RoundedCornerShape(20.dp)),
//                        colors = ButtonDefaults.buttonColors(
//                            contentColor = backgroundColor,
//                            backgroundColor = Color(0xFF0B3B2D)
//                        ),
//                    ) {
//                        Text(
//                            text = if (isNoShowing) "Updating" else "No Show",
//                            style = MaterialTheme.typography.h4,
//                            fontSize = 15.sp,
//                            modifier = Modifier.clip(RoundedCornerShape(20.dp)),
//                        )
//                    }
//                    if (!booking.isCancelled) {
//                        Button(
//                            onClick = {
//                                if (!isCancelling) {
//                                    life.lifecycleScope.launch {
//                                        cancel()
//                                    }
//                                }
//                            },
//                            modifier = Modifier
//                                .width(130.dp)
//                                .clip(RoundedCornerShape(20.dp)),
//                            colors = ButtonDefaults.buttonColors(
//                                contentColor = backgroundColor,
//                                backgroundColor = Color(0xFF0B3B2D)
//                            ),
//                        ) {
//                            Text(
//                                text = if (isCancelling) "Cancelling" else "Cancel",
//                                style = MaterialTheme.typography.h4,
//                                fontSize = 15.sp,
//                                modifier = Modifier.clip(RoundedCornerShape(20.dp)),
//                            )
//                        }
//                    }
//                }
//            }
//        }
//
//    }
//}
//
//@Composable
//fun DropDownInput(placeholder: String, value: String, onValueChange: (String) -> Unit) {
//
//    Row(
//        horizontalArrangement = Arrangement.spacedBy(20.dp),
//        verticalAlignment = Alignment.CenterVertically,
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 10.dp, vertical = 2.dp)
//    ) {
//        Row(modifier = Modifier.width(120.dp), horizontalArrangement = Arrangement.SpaceBetween) {
//            Text(
//                text = placeholder,
//                style = MaterialTheme.typography.h4
//            )
//            Text(
//                text = " - ",
//                style = MaterialTheme.typography.h4
//            )
//        }
//        TextField(
//            value = value,
//            onValueChange = { onValueChange(it) },
//            singleLine = true,
//            colors = TextFieldDefaults.textFieldColors(
//                textColor = Color.White,
//                placeholderColor = Color.White,
//                cursorColor = Color.White,
//                focusedIndicatorColor = Color.White,
//                backgroundColor = Color.Transparent
//            ),
//            textStyle = MaterialTheme.typography.h4,
//            keyboardOptions = KeyboardOptions.Default.copy(
//                keyboardType = KeyboardType.Number
//            ),
//            modifier = Modifier
//                .padding(vertical = 2.dp)
//                .height(50.dp)
//        )
//    }
//}