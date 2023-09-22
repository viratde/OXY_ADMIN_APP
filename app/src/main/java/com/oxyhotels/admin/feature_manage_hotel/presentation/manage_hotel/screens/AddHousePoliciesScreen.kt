package com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.viewmodels.AddPoliciesViewModel
import com.oxyhotels.admin.common.composables.Screen
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.components.AddHotelButton
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.components.AddHotelHeader
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.components.BulletPointInput

@Composable
fun AddHousePoliciesScreen(
    viewModel: AddPoliciesViewModel,
    isEnabled:Boolean = true,
    onNext:() -> Unit
){

    val scrollState = rememberLazyListState()

    val state by viewModel.state.collectAsState()

    Screen(
        padding = 0
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(1f)
                .fillMaxHeight()
                .imePadding()
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.onBackground)
                .padding(vertical = 10.dp, horizontal = 10.dp)
                .padding(bottom = 30.dp),
            state = scrollState
        ) {


            item() {

                AddHotelHeader(text = "Enter Hotel Rules")

                BulletPointInput(
                    header = "Restrictions and Rules",
                    bulletPoints = state.restrictions,
                    onStateChange = { viewModel.updateRulesAndRestrictions(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                        .border(1.dp, Color.White, RoundedCornerShape(10.dp))
                        .padding(horizontal = 10.dp),
                    placeholderText = "Enter New Rules",
                    isEnabled = isEnabled
                )



                BulletPointInput(
                    header = "House Policies Dos",
                    bulletPoints = state.housePoliciesDos,
                    onStateChange = { viewModel.updateDos(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                        .border(1.dp, Color.White, RoundedCornerShape(10.dp))
                        .padding(horizontal = 10.dp),
                    placeholderText = "Enter New Dos",
                    isEnabled = isEnabled
                )

                BulletPointInput(
                    header = "House Policies Donts",
                    bulletPoints = state.housePoliciesDonts,
                    onStateChange = { viewModel.updateDonts(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                        .border(1.dp, Color.White, RoundedCornerShape(10.dp))
                        .padding(horizontal = 10.dp),
                    placeholderText = "Enter New Donts",
                    isEnabled = isEnabled
                )
                BulletPointInput(
                    header = "House Amenities",
                    bulletPoints = state.houseAmenities,
                    onStateChange = { viewModel.updateAmenities(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                        .border(1.dp, Color.White, RoundedCornerShape(10.dp))
                        .padding(horizontal = 10.dp),
                    placeholderText = "Enter New Amenity",
                    isEnabled = isEnabled
                )


                AddHotelButton(text = "Next", isLoading = false) {
                    onNext()
                }

            }
        }
    }
}