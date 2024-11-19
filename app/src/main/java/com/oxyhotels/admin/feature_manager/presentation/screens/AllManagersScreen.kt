package com.oxyhotels.admin.feature_manager.presentation.screens

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
import com.oxyhotels.admin.feature_manage_hotel.presentation.ManageManagerScreenRoute
import com.oxyhotels.admin.feature_manage_hotel.presentation.hotels.components.HotelButton
import com.oxyhotels.admin.feature_manage_hotel.presentation.hotels.components.HotelDetailsTextView
import com.oxyhotels.admin.feature_manager.domain.models.Manager

@Composable
fun AllManagersScreenRoute(
    isLoading: Boolean,
    managers: List<Manager>,
    onNavigateToHotelsScreen: () -> Unit = {},
    onNavigateToManageManagerScreen: (String) -> Unit = {}
) {

    Screen(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        isScrollable = false,
        padding = 10
    ) {

        if (managers.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "No Managers",
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
                    text = "All Managers",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.background
                    )
                )

                IconButton(
                    onClick = {
                        onNavigateToManageManagerScreen("${ManageManagerScreenRoute.route}?isEnabled=true&managerId=${""}")
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

            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {

                items(managers) {

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
                            categoryName = "Phone",
                            categoryValue = it.phoneNumber
                        )

                        HotelDetailsTextView(
                            categoryName = "Email",
                            categoryValue = it.email
                        )

                        HotelDetailsTextView(
                            categoryName = "Username",
                            categoryValue = it.username
                        )

                        HotelDetailsTextView(
                            categoryName = "Password",
                            categoryValue = it.password
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            HotelButton(text = "View", isLoading = false) {
                                onNavigateToManageManagerScreen("${ManageManagerScreenRoute.route}?isEnabled=false&managerId=${it._id}")
                            }
                            HotelButton(text = "Update", isLoading = false) {
                                onNavigateToManageManagerScreen("${ManageManagerScreenRoute.route}?isEnabled=true&managerId=${it._id}")
                            }
                        }

                    }

                }
            }
        }

    }

}