package com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.util

import com.oxyhotels.admin.constant.Constant
import com.oxyhotels.admin.feature_manage_hotel.domain.model.HotelStorage
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.states.AddBasicDetailState
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.states.AddImagesState
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.states.AddNearByState
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.states.AddPoliciesState
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.states.AddRoomTypeState
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.states.ImageData
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.states.NearByData
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.states.RoomType

class Converters {
    companion object {
        fun convertImageToMap(
            imageData : MutableList<ImageData>
        ): MutableMap<String, MutableList<String> >{

            val map : MutableMap<String, MutableList<String> > = mutableMapOf()
            imageData.forEach {
                map[it.type] = it.images
            }
            return map
        }

        fun convertNearbyToMap(
            nearByData : MutableList<NearByData>
        ): MutableMap<String, MutableList<String> >{
            val map : MutableMap<String, MutableList<String> > = mutableMapOf()
           nearByData.forEach {
               map[it.type] = it.locations
           }
            return map
        }

        fun convertRoomTypeToMap(
            roomTypes : MutableList<RoomType>
        ):MutableMap<String,MutableMap<String,Any> > {

            val map : MutableMap<String, MutableMap<String,Any> > = mutableMapOf()
            roomTypes.forEach {
                val aMap : MutableMap<String,Any> = mutableMapOf()
                aMap["availableRooms"] = it.availableRooms
                aMap["features"] = it.features
                map[it.type] = aMap
            }
            return map
        }

        fun convertHotelDataToBasic(
            hotelStorage: HotelStorage?
        ): AddBasicDetailState {

            if(hotelStorage == null){
                return AddBasicDetailState()
            }

            return AddBasicDetailState(
                hotelName = hotelStorage.hotelName,
                hotelId = hotelStorage.hotelId,
                phoneNo = hotelStorage.phoneNo,
                minPrice = hotelStorage.minPrice,
                maxPrice = hotelStorage.maxPrice,
                latitude = hotelStorage.latitude.toString(),
                longitude = hotelStorage.longitude.toString(),
                locationUrl = hotelStorage.locationUrl,
                hotelAddress = hotelStorage.hotelAddress,
                hotelDescription = hotelStorage.hotelDescription,
                checkInTime = hotelStorage.checkIn,
                checkOutTime = hotelStorage.checkOut,
                refundPercentage = hotelStorage.refundPercentage.toString(),
                isHotelListed = hotelStorage.isHotelListed,
                tid = hotelStorage.tid
            )
        }

        fun convertHotelDataToHousePolicies(
            hotelStorage: HotelStorage?
        ): AddPoliciesState {
            if(hotelStorage == null){
                return AddPoliciesState()
            }
            return AddPoliciesState(
                restrictions = hotelStorage.restrictions.toMutableList(),
                houseAmenities = hotelStorage.houseAmenities.toMutableList(),
                housePoliciesDonts = hotelStorage.housePoliciesDonts.toMutableList(),
                housePoliciesDos = hotelStorage.housePoliciesDos.toMutableList()
            )
        }


        fun convertHotelDataToImage(
            hotelStorage: HotelStorage?
        ): AddImagesState {

            if(hotelStorage == null){
                return AddImagesState()
            }

            val newImageData : MutableList<ImageData> = mutableListOf()

            for(i in hotelStorage.imageData){

                val images : MutableList<String> = mutableListOf()

                for (j in i.value){
                    images.add("${Constant.DOMAIN}${j}")
                }

                newImageData.add(
                    ImageData(
                        type = i.key,
                        images= images
                    )
                )
            }

            return AddImagesState(
                imageData = newImageData
            )

        }


        fun convertHotelStorageToNearbyData(
            hotelStorage: HotelStorage?
        ): AddNearByState {


            if(hotelStorage == null){
                return AddNearByState()
            }

            val newNearByData : MutableList<NearByData> = mutableListOf()

            for(i in hotelStorage.nearBy){

                val locations : MutableList<String> = mutableListOf()

                for (j in i.value){
                    locations.add(j)
                }

                newNearByData.add(
                   NearByData(
                       type = i.key,
                       locations = locations
                   )
                )
            }
            return AddNearByState(
                nearBy = newNearByData
            )
        }

        fun convertHotelStorageToRoom(
            hotelStorage: HotelStorage?
        ): AddRoomTypeState {

            if(hotelStorage == null){
                return AddRoomTypeState()
            }
            val newRoomTypes : MutableList<RoomType> = mutableListOf()

            for (i in hotelStorage.roomTypes){
                newRoomTypes.add(
                    RoomType(
                        type = i.key,
                        availableRooms = (i.value["availableRooms"] as Double).toInt(),
                        features = i.value["features"] as MutableList<String>
                    )
                )
            }

            return AddRoomTypeState(
                roomTypes = newRoomTypes
            )

        }

    }
}