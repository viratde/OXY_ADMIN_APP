package com.oxyhotels.admin.feature_manager.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.oxyhotels.admin.feature_manage_hotel.domain.model.HotelStorage
import com.oxyhotels.admin.feature_manager.domain.models.Permission
import com.oxyhotels.admin.feature_manager.domain.models.SelectData


fun LazyListScope.ManagerPermissionsInput(
    permissions: List<Permission>,
    hotels: List<HotelStorage>,
    isStructure: Boolean = true,
    isEnabled: Boolean,
    onUpdatePermissions: (List<Permission>) -> Unit,
) {

    item {
        ManagerSelectInput(
            selected = permissions.map { it.hotel },
            label = "Choose Hotel",
            options = hotels.map { hot ->
                SelectData(
                    title = hot.hotelName,
                    value = hot._id
                )
            }
        ) {
            onUpdatePermissions(
                it.map { hotelId ->
                    permissions.find { it.hotel == hotelId } ?: (permissions.firstOrNull()?.copy(
                        hotel = hotelId
                    ) ?: Permission(hotel = hotelId))
                }
            )

        }
    }

    if (permissions.isNotEmpty()) {
        item {
            SinglePermissionInput(
                permission = permissions.first(),
                isEnabled = isEnabled,
                modifier = Modifier.padding(horizontal = 10.dp)
            ) { perm ->
                onUpdatePermissions(
                    permissions.map {
                        perm.copy(hotel = it.hotel)
                    }
                )
            }
        }
    }

}