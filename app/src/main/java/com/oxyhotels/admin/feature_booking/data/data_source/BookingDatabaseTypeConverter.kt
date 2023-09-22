package com.oxyhotels.admin.feature_booking.data.data_source

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.oxyhotels.admin.feature_booking.domain.model.BookedRoom
import com.oxyhotels.admin.feature_booking.domain.model.ExtraPrices
import java.time.ZonedDateTime


class BookingDatabaseTypeConverter {

    @TypeConverter
    fun convertDateToString(date:ZonedDateTime?):String?{
        return date?.toString()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun convertStringToDate(date:String?):ZonedDateTime?{
        return date?.let { ZonedDateTime.parse(it) }
    }

    @TypeConverter
    fun convertRoomDataToString(roomData : List<BookedRoom>) :String{
        return Gson().toJson(roomData)
    }

    @TypeConverter
    fun convertStringToRoomData(roomData:String) : List<BookedRoom> {
        val typeToken = object :TypeToken < List<BookedRoom> >(){}.type
        return Gson().fromJson(roomData,typeToken)
    }

    @TypeConverter
    fun listToString(data:List<String>):String{
        return Gson().toJson(data)
    }

    @TypeConverter
    fun stringToList(data:String):List<String> {
        val typeToken = object :TypeToken < List<String> >(){}.type
        return Gson().fromJson(data,typeToken)
    }

    @TypeConverter
    fun fromExtraPriceToList(data:List<ExtraPrices>) :String{
        return Gson().toJson(data)
    }
    @TypeConverter
    fun fromStringToExtraType(data:String):List<ExtraPrices> {
        val typeToken = object :TypeToken < List<ExtraPrices> >(){}.type
        return Gson().fromJson(data,typeToken)
    }
}