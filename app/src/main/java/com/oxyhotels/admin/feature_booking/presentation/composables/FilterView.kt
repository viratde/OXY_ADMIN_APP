package com.oxyhotels.admin.feature_booking.presentation.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.oxyhotels.admin.feature_booking.presentation.CompleteScreenRoute
import com.oxyhotels.admin.feature_booking.presentation.CreateBookingScreenRoute
import com.oxyhotels.admin.feature_booking.presentation.InHouseScreenRoute
import com.oxyhotels.admin.feature_booking.presentation.PendingScreenRoute
import com.oxyhotels.admin.feature_booking.presentation.UpcomingScreenRoute


@Composable
fun FilterView(
    navController:NavController,
){
    val horizontalState = rememberScrollState()
    val backStackState = navController.currentBackStackEntryAsState()
    AnimatedVisibility(visible = backStackState.value?.destination?.route != CreateBookingScreenRoute.route) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, top = 5.dp, bottom = 5.dp, end = 10.dp)
                .horizontalScroll(state = horizontalState)
        ) {

            FilterButton(
                value = "Upcoming",
                isSelected = backStackState.value?.destination?.route == UpcomingScreenRoute.route
            ) {
                navController.navigate(UpcomingScreenRoute.route) {
                    launchSingleTop = true
                }
            }

            FilterButton(
                value = "InHouse",
                isSelected = backStackState.value?.destination?.route == InHouseScreenRoute.route
            ) {
                navController.navigate(InHouseScreenRoute.route) {
                    launchSingleTop = true
                }
            }

            FilterButton(
                value = "Completed",
                isSelected = backStackState.value?.destination?.route == CompleteScreenRoute.route
            ) {
                navController.navigate(CompleteScreenRoute.route) {
                    launchSingleTop = true
                }
            }

            FilterButton(
                value = "Pending",
                isSelected = backStackState.value?.destination?.route == PendingScreenRoute.route
            ) {
                navController.navigate(PendingScreenRoute.route) {
                    launchSingleTop = true
                }
            }

        }
    }
}