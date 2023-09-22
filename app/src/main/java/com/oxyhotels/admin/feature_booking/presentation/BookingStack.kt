package com.oxyhotels.admin.feature_booking.presentation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.oxyhotels.admin.di.ViewModelFactoryProvider
import com.oxyhotels.admin.feature_booking.presentation.screens.BookingScreen
import com.oxyhotels.admin.feature_booking.presentation.screens.CreateBookingScreen
import com.oxyhotels.admin.feature_booking.presentation.screens.InHouseScreen
import com.oxyhotels.admin.feature_booking.presentation.screens.PendingScreen
import com.oxyhotels.admin.feature_booking.presentation.screens.UpcomingScreen
import com.oxyhotels.admin.feature_booking.presentation.viewmodels.BookingStoreViewModel
import com.oxyhotels.admin.feature_booking.presentation.viewmodels.BookingViewModel
import com.oxyhotels.manager.feature_booking.presentation.screens.CompleteScreen
import com.oxyhotels.admin.feature_booking.presentation.screens.QrScannerScreen
import dagger.hilt.android.EntryPointAccessors


interface BookingStackRoute{
    val route:String
}

object InHouseScreenRoute : BookingStackRoute {
    override val route: String = "In House Screen Route"
}

object UpcomingScreenRoute : BookingStackRoute {
    override val route: String = "Upcoming Screen Route"
}

object CompleteScreenRoute : BookingStackRoute {
    override val route: String = "Complete Screen Route"
}

object CreateBookingScreenRoute : BookingStackRoute {
    override val route: String = "Create Booking Screen Route"
}

object QrScannerScreenRoute  : BookingStackRoute {
    override val route: String = "Qr Scanner Screen Route"
}

object BookingScreenRoute : BookingStackRoute {
    override val route: String = "Booking Screen Route"
}

object PendingScreenRoute: BookingStackRoute {
    override val route: String = "Pending Screen Route"
}

@Composable
fun BookingStack(
    bookingStoreViewModel: BookingStoreViewModel,
    token:String,
) {
    val navController = rememberNavController()

    val state = bookingStoreViewModel.state.collectAsState()


    NavHost(navController = navController, startDestination = UpcomingScreenRoute.route) {

        composable(UpcomingScreenRoute.route) {
            UpcomingScreen(
                navController = navController,
                bookingStoreViewModel = bookingStoreViewModel,
            )
        }


        composable(QrScannerScreenRoute.route){
            QrScannerScreen(
                navController = navController,
                qrScannerViewModel = hiltViewModel()
            )
        }


        composable(InHouseScreenRoute.route) {

            InHouseScreen(
                navController = navController,
                bookingStoreViewModel = bookingStoreViewModel,
            )
        }


        composable(CompleteScreenRoute.route) {

            CompleteScreen(
                navController = navController,
                bookingStoreViewModel = bookingStoreViewModel,
            )
        }

        composable(PendingScreenRoute.route){
            val factory = EntryPointAccessors.fromActivity(
                LocalContext.current as Activity,
                ViewModelFactoryProvider::class.java
            )
            val pendingViewModel = factory.createPendingViewModelFactoryProvider().create(
                token = token,
                hotelId = bookingStoreViewModel.hotelId
            )
            PendingScreen(
                pendingViewModel = pendingViewModel,
                navController = navController
            )
        }

        composable(CreateBookingScreenRoute.route +"/hotelId={hotelId}") {

            val hotelId = it.arguments?.getString("hotelId")
            if (hotelId.isNullOrEmpty()) {
                return@composable
            }

            val factory = EntryPointAccessors.fromActivity(
                LocalContext.current as Activity,
                ViewModelFactoryProvider::class.java
            )
            val createBookingViewModel = factory.createBookingViewModelFactoryProvider()
                .create(hotelId = hotelId, token = token)

            CreateBookingScreen(
                navController = navController,
                createBookingViewModel = createBookingViewModel,
            )
        }

        composable(
            "${BookingScreenRoute.route}?id={id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) {

            val bookingId = it.arguments?.getString("id")?.toIntOrNull()

            val bookingModel =
                (state.value.inHouseBookings + state.value.completeBookings + state.value.upcomingBookings).find { book ->
                    book.bookingId == bookingId
                }

            if (bookingModel == null) {
                navController.popBackStack()
                return@composable
            }

            val bookingViewModel = BookingViewModel(
                bookingModel = bookingModel,
                token = token,
                bookingUseCases = bookingStoreViewModel.bookingUseCases
            )

            BookingScreen(
                bookingViewModel = bookingViewModel
            )
        }

    }
}
