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
    fun fromRoomTypes(roomTypes: MutableMap<String, MutableMap<String, Any>>): String {
        return Gson().toJson(roomTypes)
    }

    @TypeConverter
    fun toRoomTypes(roomTypes: String): MutableMap<String, MutableMap<String, Any>> {

        val typeToken = object : TypeToken<MutableMap<String, MutableMap<String, Any>>>() {}.type

        return Gson().fromJson(roomTypes, typeToken)
    }

    @TypeConverter
    fun fromFloorData(structure: List<List<ActualRoom>>): String {
        return Gson().toJson(structure)
    }

    @TypeConverter
    fun toFloorData(data:String):List<List<ActualRoom>> {
        val typeToken = object : TypeToken<List<List<ActualRoom>>>() {}.type
        return Gson().fromJson(data, typeToken)
    }

}