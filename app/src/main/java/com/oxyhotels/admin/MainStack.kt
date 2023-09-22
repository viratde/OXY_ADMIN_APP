package com.oxyhotels.admin

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.oxyhotels.admin.feature_accounting.presentation.screens.AccountingScreen
import com.oxyhotels.admin.feature_manage_hotel.presentation.HotelStack

interface MainStackRoute {
    val route: String
}

object HotelStackRoute:MainStackRoute{
    override val route: String = "Hotel Stack Route"
}

object AccountingStackRoute:MainStackRoute{
    override val route: String = "Accounting Stack Route"
}

@Composable
fun MainStack(
    token:String
){
    
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = HotelStackRoute.route){
        composable(HotelStackRoute.route){
            HotelStack(
                token = token
            )
        }
        composable(AccountingStackRoute.route){
            AccountingScreen(
                navController = navController,
                accountingViewModel = hiltViewModel()
            )
        }
    }
    
}


