package com.oxyhotels.admin.feature_location.presentation.screens

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import com.oxyhotels.admin.common.composables.Screen
import com.oxyhotels.admin.feature_location.domain.models.Location
import com.oxyhotels.admin.feature_manage_hotel.presentation.ManageLocationScreenRoute
import com.oxyhotels.admin.feature_manage_hotel.presentation.ManageManagerScreenRoute
import com.oxyhotels.admin.feature_manage_hotel.presentation.hotels.components.HotelButton
import com.oxyhotels.admin.feature_manage_hotel.presentation.hotels.components.HotelDetailsTextView
import com.oxyhotels.admin.feature_manager.domain.models.Manager

@Composable
fun AllLocationsScreen(
    isLoading: Boolean,
    locations: List<Location>,
    onNavigateToHotelsScreen: () -> Unit = {},
    onNavigateToEditLocationScreen: (String) -> Unit = {}
) {

    Screen(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        isScrollable = false,
        padding = 10
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 10.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(MaterialTheme.colorScheme.secondary)
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "All Locations",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.background
                )
            )

            IconButton(
                onClick = {
                    onNavigateToEditLocationScreen("${ManageLocationScreenRoute.route}?isEnabled=true&locationId=${""}")
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "",
                    modifier = Modifier.size(18.dp),
                    tint = MaterialTheme.colorScheme.background
                )
            }
        }

        if (locations.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {


                Text(
                    text = "No Locations",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 12.sp
                    ),
                    color = Color.White
                )
            }
        }

        Column(
            modifier = Modifier
                .widthIn(max = 330.dp, min = 300.dp)
        ) {


            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {

                items(locations) {

                    Column(
                        modifier = Modifier
                            .padding(vertical = 10.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.onBackground)
                            .clickable(onClick = {

                            })
                            .padding(10.dp)
                    ) {


                        HotelDetailsTextView(
                            categoryName = "Name",
                            categoryValue = it.name
                        )

                        HotelDetailsTextView(
                            categoryName = "Latitude",
                            categoryValue = it.latitude.toString()
                        )

                        HotelDetailsTextView(
                            categoryName = "Longitude",
                            categoryValue = it.longitude.toString()
                        )

                        HotelDetailsTextView(
                            categoryName = "Distance",
                            categoryValue = it.distance.toString()
                        )


                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            HotelButton(text = "View", isLoading = false) {
                                onNavigateToEditLocationScreen("${ManageLocationScreenRoute.route}?isEnabled=false&locationId=${it._id}")
                            }
                            HotelButton(text = "Update", isLoading = false) {
                                onNavigateToEditLocationScreen("${ManageLocationScreenRoute.route}?isEnabled=true&locationId=${it._id}")
                            }
                        }

                    }

                }
            }
        }

    }


}