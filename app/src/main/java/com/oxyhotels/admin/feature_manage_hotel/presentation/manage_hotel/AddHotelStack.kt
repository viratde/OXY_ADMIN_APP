package com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.oxyhotels.admin.feature_manage_hotel.domain.model.HotelStorage
import com.oxyhotels.admin.feature_manage_hotel.presentation.AllHotelsRoute
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.screens.AddBasicDetailScreen
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.screens.AddHotelStructureScreen
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.screens.AddHousePoliciesScreen
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.screens.AddImagesScreen
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.screens.AddNearByScreen
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.screens.AddRoomTypeScreen
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.states.AddHotelStructureState
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.util.AddHotelData
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.util.Converters
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.util.DataValidation
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.viewmodels.AddBasicDetailViewModel
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.viewmodels.AddHotelStructureViewModel
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.viewmodels.AddImagesViewModel
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.viewmodels.AddNearByViewModel
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.viewmodels.AddPoliciesViewModel
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.viewmodels.AddRoomTypeViewModel
import kotlinx.coroutines.launch

interface AddHotelStack {
    val route: String
}

object AddBasicDetailsScreenRoute : AddHotelStack {
    override val route: String = "Add Basic Details Screen Route"
}

object AddImagesScreenRoute : AddHotelStack {
    override val route: String = "Add Images Screen Route"
}

object AddNearByScreenRoute : AddHotelStack {
    override val route: String = "Add Nearby Screen Routes"
}

object AddHousePoliciesScreenRoute : AddHotelStack {
    override val route: String = "Add House Policies Screen Route"
}

object AddRoomTypeScreenRoute : AddHotelStack {
    override val route: String = "Add Room Type Screen Route"
}

object CreateStructureScreenRoute : AddHotelStack {
    override val route: String = "Create Hotel Structure Route"
}

@Composable
fun AddHotelStack(
    viewModel: AddHotelViewModel,
    isEnabled: Boolean,
    hotelStorage: HotelStorage? = null,
    pNavController: NavHostController,
    onSave: (HotelStorage) -> Unit,
) {

    val state = viewModel.state.collectAsState()
    val addBasicDetailViewModel = remember {
        AddBasicDetailViewModel(Converters.convertHotelDataToBasic(hotelStorage))
    }
    val addImagesViewModel = remember {
        AddImagesViewModel(Converters.convertHotelDataToImage(hotelStorage))
    }
    val addNearByViewModel = remember {
        AddNearByViewModel(Converters.convertHotelStorageToNearbyData(hotelStorage))
    }
    val addPoliciesViewModel = remember {
        AddPoliciesViewModel(Converters.convertHotelDataToHousePolicies(hotelStorage))
    }
    val addRoomTypeViewModel = remember {
        AddRoomTypeViewModel(Converters.convertHotelStorageToRoom(hotelStorage))
    }
    val addHotelStructureViewModel = remember {
        AddHotelStructureViewModel(
            addHotelStructureState = AddHotelStructureState(
                hotelStorage?.hotelStructure?.map { it.value } ?: mutableListOf()
            )
        )
    }

    val dataValidation = remember {
        DataValidation()
    }

    val context = LocalContext.current

    fun showMessage(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    LaunchedEffect(key1 = state.value.isError) {
        if (state.value.isError) {
            showMessage(state.value.errorMessage)
            viewModel.clearMessage()
        }
    }

    val navController = rememberNavController()
    val life = LocalLifecycleOwner.current


    fun prepareData() {
        val hotelData = AddHotelData(
            hotelName = addBasicDetailViewModel.state.value.hotelName,
            hotelId = addBasicDetailViewModel.state.value.hotelId,
            phoneNo = addBasicDetailViewModel.state.value.phoneNo,
            latitude = addBasicDetailViewModel.state.value.latitude,
            longitude = addBasicDetailViewModel.state.value.longitude,
            minPrice = addBasicDetailViewModel.state.value.minPrice,
            maxPrice = addBasicDetailViewModel.state.value.maxPrice,
            locationUrl = addBasicDetailViewModel.state.value.locationUrl,
            hotelAddress = addBasicDetailViewModel.state.value.hotelAddress,
            hotelDescription = addBasicDetailViewModel.state.value.hotelDescription,
            checkIn = addBasicDetailViewModel.state.value.checkInTime,
            checkOut = addBasicDetailViewModel.state.value.checkOutTime,
            refundPercentage = addBasicDetailViewModel.state.value.refundPercentage,
            restrictions = addPoliciesViewModel.state.value.restrictions,
            housePoliciesDos = addPoliciesViewModel.state.value.housePoliciesDos,
            housePoliciesDonts = addPoliciesViewModel.state.value.housePoliciesDonts,
            houseAmenities = addPoliciesViewModel.state.value.houseAmenities,
            imageData = Converters.convertImageToMap(addImagesViewModel.state.value.imageData),
            roomTypes = Converters.convertRoomTypeToMap(addRoomTypeViewModel.state.value.roomTypes),
            nearBy = Converters.convertNearbyToMap(addNearByViewModel.state.value.nearBy),
            hotelStructure = addHotelStructureViewModel.state.value.structure,
            isHotelListed = addBasicDetailViewModel.state.value.isHotelListed,
            tid = addBasicDetailViewModel.state.value.tid
        )

        life.lifecycleScope.launch {
            if (hotelStorage == null) {
                val savedData = viewModel.saveHotel(hotelData, context)
                if (savedData != null) {
                    onSave(savedData)
                }
            } else {
                val savedData = viewModel.updateHotel(hotelData, context, hotelStorage._id)
                if (savedData != null) {
                    onSave(savedData)
                }
            }
        }

    }


    NavHost(navController = navController, startDestination = AddBasicDetailsScreenRoute.route) {
        composable(AddBasicDetailsScreenRoute.route) {
            AddBasicDetailScreen(
                viewModel = addBasicDetailViewModel,
                isEnabled = isEnabled
            ) {
                val check = dataValidation.basicDataValidation(addBasicDetailViewModel.state.value)
                if (check.isNotEmpty()) {
                    showMessage(check)
                } else {
                    navController.navigate(AddHousePoliciesScreenRoute.route) {
                        launchSingleTop = true;
                    }
                }
            }
        }
        composable(AddHousePoliciesScreenRoute.route) {
            AddHousePoliciesScreen(
                viewModel = addPoliciesViewModel,
                isEnabled = isEnabled
            ) {
                val check = dataValidation.housePoliciesValidation(addPoliciesViewModel.state.value)
                if (check.isNotEmpty()) {
                    showMessage(check)
                } else {
                    navController.navigate(AddImagesScreenRoute.route) {
                        launchSingleTop = true;
                    }
                }
            }
        }
        composable(AddImagesScreenRoute.route) {
            AddImagesScreen(
                viewModel = addImagesViewModel,
                isEnabled = isEnabled
            ) {
                val check = dataValidation.imageValidation(addImagesViewModel.state.value)
                if (check.isNotEmpty()) {
                    showMessage(check)
                } else {
                    navController.navigate(AddNearByScreenRoute.route) {
                        launchSingleTop = true
                    }
                }
            }
        }
        composable(AddNearByScreenRoute.route) {
            AddNearByScreen(
                viewModel = addNearByViewModel,
                isEnabled = isEnabled
            ) {
                val check = dataValidation.nearbyValidation(addNearByViewModel.state.value)
                if (check.isNotEmpty()) {
                    showMessage(check)
                } else {
                    navController.navigate(AddRoomTypeScreenRoute.route) {
                        launchSingleTop = true
                    }
                }
            }
        }
        composable(AddRoomTypeScreenRoute.route) {
            AddRoomTypeScreen(
                viewModel = addRoomTypeViewModel,
                isEnabled = isEnabled,
                isLoading = state.value.isLoading
            ) {
                val check = dataValidation.roomTypeValidation(addRoomTypeViewModel.state.value)
                if (check.isNotEmpty()) {
                    showMessage(check)
                } else {
                    navController.navigate(CreateStructureScreenRoute.route) {
                        launchSingleTop = true
                    }
                }
            }
        }

        composable(CreateStructureScreenRoute.route) {

            AddHotelStructureScreen(
                viewModel = addHotelStructureViewModel,
                isLoading = state.value.isLoading,
                addRoomTypeViewModel = addRoomTypeViewModel,
                isEnabled = isEnabled
            ) {
                if (isEnabled) {
                    val check = dataValidation.floorTypeDataValidation(
                        it,
                        addHotelStructureViewModel.state.value
                    )
                    if (check.isNotEmpty()) {
                        showMessage(check)
                    } else {
                        prepareData()
                    }
                } else {
                    pNavController.navigate(AllHotelsRoute.route) {
                        launchSingleTop = true
                        popUpTo(pNavController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                }
            }
        }

    }
}