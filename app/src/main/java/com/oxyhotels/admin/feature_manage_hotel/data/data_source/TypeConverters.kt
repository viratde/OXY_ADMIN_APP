package com.oxyhotels.admin.feature_manage_hotel.data.data_source

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.oxyhotels.admin.feature_manage_hotel.domain.util.ActualRoom
import io.ktor.util.reflect.Type

class TypeConverters {


    @TypeConverter
    fun fromStringToArray(value: String): List<String> {
        val typeToken = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, typeToken)
    }

    @TypeConverter
    fun fromArrayToString(value: List<String>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun fromStringToMap(value: String): Map<String, List<String>> {
        val typeToken = object : TypeToken<Map<String, List<String>>>() {}.type
        return Gson().fromJson(value, typeToken)
    }

    @TypeConverter
    fun fromMapToString(value: Map<String, List<String>>): String {
        return Gson().toJson(value)
    }


    @TypeConverter
    fun fromRoomTypes(roomTypes: Map<String, Map<String, Any>>): String {
        return Gson().toJson(roomTypes)
    }

    @TypeConverter
    fun toRoomTypes(roomTypes: String): Map<String, Map<String, Any>> {

        val typeToken = object : TypeToken<Map<String, Map<String, Any>>>() {}.type
        return Gson().fromJson(roomTypes, typeToken)
    }

    @TypeConverter
    fun fromFloorData(structure: Map<String,List<ActualRoom>>): String {
        return Gson().toJson(structure)
    }

    @TypeConverter
    fun toFloorData(data:String):Map<String,List<ActualRoom>> {
        val typeToken = object : TypeToken<Map<String,List<ActualRoom>>>() {}.type
        return Gson().fromJson(data, typeToken)
    }

}