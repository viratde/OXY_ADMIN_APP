package com.oxyhotels.admin.feature_manage_hotel.presentation

import android.app.Activity
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.oxyhotels.admin.di.ViewModelFactoryProvider
import com.oxyhotels.admin.feature_analytics.presentation.screens.AnalyticsScreen
import com.oxyhotels.admin.feature_booking.presentation.BookingStack
import com.oxyhotels.admin.feature_manage_hotel.domain.model.HotelStorage
import com.oxyhotels.admin.feature_manage_hotel.presentation.hotels.HotelsViewModel
import com.oxyhotels.admin.feature_manage_hotel.presentation.hotels.components.AllHotelsScreen
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.AddHotelStack
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.AddHotelViewModel
import com.oxyhotels.admin.feature_manage_hotel.presentation.pricing.AddPricingScreen
import com.oxyhotels.admin.feature_manage_hotel.presentation.pricing.AddPricingViewModel
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.launch


interface HotelStackRoute{
    val route:String
}
object AllHotelsRoute: HotelStackRoute {
    override val route: String = "All Hotels Screen"
}

object AddHotelRoute: HotelStackRoute {
    override val route: String = "Add Hotel Route"
}

object AddPriceScreenRoute: HotelStackRoute {
    override val route: String = "Add Pricing Screen"
}

object BookingManageRoute:HotelStackRoute{
    override val route: String = "Manage Bookings Route"
}

object AnalyticsScreenRoute:HotelStackRoute{
    override val route: String = "Analytics Screen Route"
}

@Composable
fun HotelStack(
    token:String
){
    val viewModel: AddHotelViewModel =  hiltViewModel()
    val navController= rememberNavController()
    val life = rememberCoroutineScope()

    val context = LocalContext.current

    val mainViewModel : HotelsViewModel = hiltViewModel()
    val addPricingViewModel : AddPricingViewModel = hiltViewModel()

    fun showMessage(msg:String){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()
    }

    val state = mainViewModel.state

    val onSave:(HotelStorage) -> Unit = remember {
        {
            life.launch {
                mainViewModel.addNewHotel(it)
                navController.navigate(AllHotelsRoute.route){
                    launchSingleTop =true
                    popUpTo(navController.graph.startDestinationId){
                        inclusive=true
                    }
                }
            }
        }

    }


    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        ViewModelFactoryProvider::class.java
    )

    NavHost(navController = navController, startDestination = AllHotelsRoute.route){
        composable(AllHotelsRoute.route){
            AllHotelsScreen(
                navController = navController,
                state = state.value,
            )
        }

        composable(AnalyticsScreenRoute.route){
            AnalyticsScreen(analyticsViewModel = hiltViewModel())
        }

        composable(
            "${AddPriceScreenRoute.route}?hotelId={hotelId}",
            arguments = listOf(
                navArgument("hotelId"){
                    defaultValue = ""
                    type = NavType.StringType
                }
            )
        ){

            val hotelId = it.arguments?.getString("hotelId")

            var hotelStorage: HotelStorage? = null

            if(hotelId != null && hotelId != ""){
                println(hotelId)
                hotelStorage = state.value.hotels.filter { hotelData ->
                    hotelData._id == hotelId
                }[0]
            }
            if(hotelStorage == null){
                showMessage("Hotel Not Found")
                navController.navigate(AllHotelsRoute.route){
                    launchSingleTop = true
                    popUpTo(navController.graph.startDestinationId){
                        inclusive = true
                    }
                }
                return@composable
            }

            AddPricingScreen(
                viewModel = addPricingViewModel,
                hotelId = hotelStorage._id,
                values = hotelStorage.roomTypes.keys.toList(),
                navController = navController,
                isEnabled= true
            )
        }

        composable(BookingManageRoute.route + "/hotelId={hotelId}") {
            val hotelId = it.arguments?.getString("hotelId")
            if (hotelId.isNullOrEmpty()) {
                return@composable
            }
            val bookingStoreViewModel = factory.bookingStoreViewModelFactoryProvider()
                .create(hotelId = hotelId, token = token)

            BookingStack(
                bookingStoreViewModel = bookingStoreViewModel,
                token = token
            )
        }


        composable("${AddHotelRoute.route}?isEnabled={isEnabled}&hotelId={hotelId}",
            arguments = listOf(
                navArgument(
                    name ="isEnabled",
                ){
                    defaultValue = true
                    type = NavType.BoolType
                },
                navArgument("hotelId"){
                    defaultValue = ""
                    type = NavType.StringType
                }
            )
        ){
            val isEnabled = it.arguments?.getBoolean("isEnabled")
            val hotelId = it.arguments?.getString("hotelId")

            var hotelStorage: HotelStorage? = null

            if(hotelId != null && hotelId != ""){
                println(hotelId)
                hotelStorage = state.value.hotels.filter { hotelData ->
                    hotelData._id == hotelId
                }[0]
            }

            AddHotelStack(
               viewModel = viewModel,
                isEnabled = (isEnabled == true),
                hotelStorage = hotelStorage,
                pNavController =navController,
                onSave = onSave
            )
        }
    }
}