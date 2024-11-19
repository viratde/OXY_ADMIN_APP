package com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.util

import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.states.AddBasicDetailState
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.states.AddHotelStructureState
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.states.AddImagesState
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.states.AddNearByState
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.states.AddPoliciesState
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.states.AddRoomTypeState

class DataValidation {

    fun basicDataValidation(addBasicDetailState: AddBasicDetailState): String {

        if (addBasicDetailState.hotelName.isEmpty()) {
            return "Please Enter Correct Hotel Name";
        }

        if (addBasicDetailState.hotelId.isEmpty()) {
            return "Please Enter Correct Hotel Id";
        }

        if (addBasicDetailState.phoneNo.isEmpty() || addBasicDetailState.phoneNo.length != 12) {
            return "Please Enter Correct Phone Number";
        }

        if (addBasicDetailState.minPrice == 0) {
            return "Please Enter Correct Minimum Price";
        }

        if (addBasicDetailState.maxPrice == 0) {
            return "Please Enter Correct Maximum Price";
        }

        if (addBasicDetailState.latitude.isEmpty()) {
            return "Please Enter Correct Latitude";
        }

        if (addBasicDetailState.longitude.isEmpty()) {
            return "Please Enter Correct Longitude";
        }

        if (addBasicDetailState.locationUrl.isEmpty()) {
            return "Please Enter Correct Location Url";
        }

        if (addBasicDetailState.hotelAddress.isEmpty()) {
            return "Please Enter Correct Address";
        }

        if (addBasicDetailState.hotelDescription.isEmpty() || addBasicDetailState.hotelDescription.length < 100) {
            return "Please Enter Correct Description of 100 letters";
        }
        return ""
    }


    fun housePoliciesValidation(addPoliciesState: AddPoliciesState): String {

        if (addPoliciesState.housePoliciesDonts.size == 0) {
            return "Please Enter Correct House Policies Donts"
        }

        if (addPoliciesState.housePoliciesDos.size == 0) {
            return "Please Enter Correct House Policies Dos"
        }

        if (addPoliciesState.restrictions.size == 0) {
            return "Please Enter Correct Restrictions"
        }

        if (addPoliciesState.houseAmenities.size == 0) {
            return "Please Enter Correct House Amenities"
        }
        return ""
    }

    fun imageValidation(addImagesState: AddImagesState): String {

        if (addImagesState.imageData.size == 0) {
            return "Please Upload Images"
        }

        for (i in addImagesState.imageData) {

            if (i.type.isEmpty()) {
                return "Please Enter Type Name of the Images"
            }

            if (i.images.size == 0) {
                return "Please remove extra image types"
            }

        }
        return ""
    }

    fun nearbyValidation(addNearByState: AddNearByState): String {


        for (i in addNearByState.nearBy) {

            if (i.type.isEmpty()) {
                return "Please Enter Type Name of the Nearby"
            }

            if (i.locations.size == 0) {
                return "Please remove extra Nearbys"
            }

        }
        return ""
    }

    fun roomTypeValidation(addRoomTypeState: AddRoomTypeState): String {

        if (addRoomTypeState.roomTypes.size == 0) {
            return "Please add Room Types"
        }

        for (i in addRoomTypeState.roomTypes) {

            if (i.type.isEmpty()) {
                return "Please Enter Type Name of the Nearby"
            }

            if (i.availableRooms == 0) {
                return "Please Enter Correct No of Available Rooms"
            }

        }
        return ""
    }

    fun floorTypeDataValidation(
        addRoomTypeState: AddRoomTypeState,
        addHotelStructureState: AddHotelStructureState
    ): String {

        val roomTypes = addRoomTypeState.roomTypes.toList()
        val floorRooms = addHotelStructureState.structure.flatten()

        for (i in roomTypes) {
            val typeRooms = floorRooms.filter { it.roomType == i.type }.size
            if (typeRooms < i.availableRooms) {
                return "Add More ${i.type} Rooms"
            } else if (typeRooms > i.availableRooms) {
                return "Remove Some ${i.type} Rooms"
            }
        }

        val allTypes = floorRooms.map { it.roomType }.distinct()
        val allExistTypes = roomTypes.map { it.type }.distinct()
        val allRoomNos = floorRooms.map { it.roomNo }

        if (allRoomNos.size != allRoomNos.distinct().size) {
            return "Two Rooms cannot have same name."
        }

        for (i in allTypes) {
            if (!allExistTypes.contains(i)) {
                return "$i Rooms does not exist in this hotel."
            }
        }
        return ""
    }
}