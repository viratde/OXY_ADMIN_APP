package com.oxyhotels.admin.feature_manage_hotel.presentation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.oxyhotels.admin.feature_location.domain.models.Location
import com.oxyhotels.admin.feature_location.domain.models.LocationData
import com.oxyhotels.admin.feature_location.presentation.screens.AllLocationsScreen
import com.oxyhotels.admin.feature_location.presentation.screens.ManageLocationScreen
import com.oxyhotels.admin.feature_location.presentation.viewmodels.LocationViewModel
import com.oxyhotels.admin.feature_manage_hotel.domain.model.HotelStorage
import com.oxyhotels.admin.feature_manage_hotel.presentation.hotels.HotelsViewModel
import com.oxyhotels.admin.feature_manage_hotel.presentation.hotels.components.AllHotelsScreen
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.AddHotelStack
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.AddHotelViewModel
import com.oxyhotels.admin.feature_manager.domain.models.Manager
import com.oxyhotels.admin.feature_manager.presentation.screens.AllManagersScreenRoute
import com.oxyhotels.admin.feature_manager.presentation.screens.ManageManagerScreen
import com.oxyhotels.admin.feature_manager.presentation.viewmodels.AllManagersViewmodel
import kotlinx.coroutines.launch


interface HotelStackRoute {
    val route: String
}

object AllHotelsRoute : HotelStackRoute {
    override val route: String = "All Hotels Screen"
}

object AddHotelRoute : HotelStackRoute {
    override val route: String = "Add Hotel Route"
}

object ManagerScreensRoute : HotelStackRoute {
    override val route: String = "All Managers Screen Route"
}

object ManageManagerScreenRoute : HotelStackRoute {
    override val route: String = "Manage Managers Screen Route"
}

object AllLocationsScreenRoute : HotelStackRoute {
    override val route: String = "All Locations Screen Route"
}

object ManageLocationScreenRoute : HotelStackRoute {
    override val route: String = "Manage Location Screen Route"
}

@Composable
fun HotelStack(
) {
    val viewModel: AddHotelViewModel = hiltViewModel()
    val navController = rememberNavController()
    val life = rememberCoroutineScope()
    val context = LocalContext.current
    fun showMessage(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    val mainViewModel: HotelsViewModel = hiltViewModel()

    val state = mainViewModel.state

    val onSave: (HotelStorage) -> Unit = remember {
        {
            life.launch {
                mainViewModel.addNewHotel(it)
                navController.navigate(AllHotelsRoute.route) {
                    launchSingleTop = true
                    popUpTo(navController.graph.startDestinationId) {
                        inclusive = true
                    }
                }
            }
        }

    }

    val allManagersViewmodel: AllManagersViewmodel = hiltViewModel()
    val managersState = allManagersViewmodel.state.collectAsState()

    LaunchedEffect(key1 = managersState.value.isError) {
        if (managersState.value.isError) {
            showMessage(managersState.value.errorMessage)
            allManagersViewmodel.clearMessage()
            if (managersState.value.errorMessage == "Manager Updated Successfully.") {
                navController.popBackStack()
            }
        }
    }

    val locationViewmodel: LocationViewModel = hiltViewModel()
    val locationState by locationViewmodel.state.collectAsState()

    LaunchedEffect(key1 = locationState.isError) {
        if (locationState.isError) {
            showMessage(locationState.errorMessage)
            locationViewmodel.clearMessage()
            if (locationState.errorMessage == "Location Updated Successfully.") {
                navController.popBackStack()
            }
        }
    }


    NavHost(navController = navController, startDestination = AllHotelsRoute.route) {
        composable(AllHotelsRoute.route) {
            AllHotelsScreen(
                navController = navController,
                state = state.value,
            )
        }

        composable(AllLocationsScreenRoute.route) {

            AllLocationsScreen(
                isLoading = locationState.isLocationsLoading,
                locations = locationState.locations
            ) {
                navController.navigate(it) {
                    launchSingleTop = true
                }
            }

        }

        composable("${ManageLocationScreenRoute.route}?isEnabled={isEnabled}&locationId={locationId}",
            arguments = listOf(navArgument(
                name = "isEnabled",
            ) {
                defaultValue = true
                type = NavType.BoolType
            }, navArgument("locationId") {
                defaultValue = ""
                type = NavType.StringType
            })
        ) { nav ->

            val isEnabled = nav.arguments?.getBoolean("isEnabled") ?: false
            var location by remember {
                mutableStateOf(
                    locationState.locations.find {
                        it._id == nav.arguments?.getString("locationId")
                    }?.let {
                        LocationData(
                            latitude = it.latitude.toString(),
                            longitude = it.longitude.toString(),
                            distance = it.distance.toString(),
                            name = it.distance.toString(),
                            _id = it._id
                        )
                    } ?: LocationData()
                )
            }

            ManageLocationScreen(
                isLoading = locationState.isLocationUpdating,
                onUpdateLocation = {
                    location = it
                },
                locationData = location,
                isEnabled = isEnabled
            ) {
                locationViewmodel.updateManager(location)
            }

        }


        composable(ManagerScreensRoute.route) {

            AllManagersScreenRoute(isLoading = managersState.value.isManagersLoading,
                managers = managersState.value.managers,
                onNavigateToManageManagerScreen = {
                    navController.navigate(it) {
                        launchSingleTop = true
                    }
                })

        }

        composable("${ManageManagerScreenRoute.route}?isEnabled={isEnabled}&managerId={managerId}",
            arguments = listOf(navArgument(
                name = "isEnabled",
            ) {
                defaultValue = true
                type = NavType.BoolType
            }, navArgument("managerId") {
                defaultValue = ""
                type = NavType.StringType
            })
        ) { nav ->

            val isEnabled = nav.arguments?.getBoolean("isEnabled") ?: false
            var manager by remember {
                mutableStateOf(

                    managersState.value.managers.find {
                        it._id == nav.arguments?.getString("managerId")
                    } ?: Manager()
                )
            }

            ManageManagerScreen(
                isLoading = managersState.value.isManagerUpdating,
                onUpdateManager = {
                    manager = it
                },
                manager = manager,
                isEnabled = isEnabled,
                hotels = state.value.hotels,
            ) {
                allManagersViewmodel.updateManager(manager)
            }

        }


        composable("${AddHotelRoute.route}?isEnabled={isEnabled}&hotelId={hotelId}",
            arguments = listOf(navArgument(
                name = "isEnabled",
            ) {
                defaultValue = true
                type = NavType.BoolType
            }, navArgument("hotelId") {
                defaultValue = ""
                type = NavType.StringType
            })
        ) {
            val isEnabled = it.arguments?.getBoolean("isEnabled")
            val hotelId = it.arguments?.getString("hotelId")

            var hotelStorage: HotelStorage? = null

            if (hotelId != null && hotelId != "") {
                hotelStorage = state.value.hotels.filter { hotelData ->
                    hotelData._id == hotelId
                }[0]
            }

            AddHotelStack(
                viewModel = viewModel,
                isEnabled = (isEnabled == true),
                hotelStorage = hotelStorage,
                pNavController = navController,
                onSave = onSave
            )

        }
    }
}