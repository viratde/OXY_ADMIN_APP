package com.oxyhotels.admin.feature_manage_hotel.presentation.hotels.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.oxyhotels.admin.common.composables.Screen
import com.oxyhotels.admin.feature_manage_hotel.presentation.AddHotelRoute
import com.oxyhotels.admin.feature_manage_hotel.presentation.AllLocationsScreenRoute
import com.oxyhotels.admin.feature_manage_hotel.presentation.ManagerScreensRoute
import com.oxyhotels.admin.feature_manage_hotel.presentation.hotels.HotelsState

@Composable

fun AllHotelsScreen(
    navController: NavController,
    state: HotelsState
) {

    Screen(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        isScrollable = true,
        padding = 10
    ) {

        if (state.hotels.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "NO Hotels",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 12.sp
                    ),
                    color = Color.Black
                )
            }
        }

        Column(
            modifier = Modifier
                .widthIn(max = 330.dp, min = 300.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(MaterialTheme.colorScheme.secondary)
                    .padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "All Hotels",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.background
                    )
                )

                Row {
                    IconButton(onClick = {
                        navController.navigate(ManagerScreensRoute.route) {
                            launchSingleTop = true
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "",
                            modifier = Modifier.size(18.dp),
                            tint = MaterialTheme.colorScheme.background
                        )
                    }

                    IconButton(onClick = {
                        navController.navigate(AllLocationsScreenRoute.route) {
                            launchSingleTop = true
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "",
                            modifier = Modifier.size(18.dp),
                            tint = MaterialTheme.colorScheme.background
                        )
                    }

                    IconButton(onClick = {
                        navController.navigate(AddHotelRoute.route) {
                            launchSingleTop = true
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "",
                            modifier = Modifier.size(18.dp),
                            tint = MaterialTheme.colorScheme.background
                        )
                    }
                }
            }

            state.hotels.map {


                Column(
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.onBackground)
                        .clickable(onClick = {
                            navController.navigate("${AddHotelRoute.route}?isEnabled=false&hotelId=${it._id}") {
                                launchSingleTop = true
                            }
                        })
                        .padding(10.dp)
                ) {


                    HotelDetailsTextView(
                        categoryName = "Hotelname",
                        categoryValue = it.hotelName
                    )

                    HotelDetailsTextView(
                        categoryName = "HotelId",
                        categoryValue = it.hotelId
                    )

                    HotelDetailsTextView(
                        categoryName = "Phone Number",
                        categoryValue = it.phoneNo
                    )

                    HotelDetailsTextView(
                        categoryName = "Minimum Price",
                        categoryValue = it.minPrice.toString()
                    )

                    HotelDetailsTextView(
                        categoryName = "Maximum Price",
                        categoryValue = it.maxPrice.toString()
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        HotelButton(text = "View", isLoading = false) {
                            navController.navigate("${AddHotelRoute.route}?isEnabled=false&hotelId=${it._id}") {
                                launchSingleTop = true
                            }
                        }
                        HotelButton(text = "Update", isLoading = false) {
                            navController.navigate("${AddHotelRoute.route}?isEnabled=true&hotelId=${it._id}") {
                                launchSingleTop = true
                            }
                        }
                    }

                }

            }
        }

    }
}
