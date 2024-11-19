package com.oxyhotels.admin.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.google.gson.Gson
import com.oxyhotels.admin.feature_auth.data.repository.AuthTokenRepositoryImpl
import com.oxyhotels.admin.feature_auth.domain.repository.AuthDataRepository
import com.oxyhotels.admin.feature_auth.domain.use_cases.AuthTokenUseCases
import com.oxyhotels.admin.feature_auth.domain.use_cases.DeleteTokenUseCases
import com.oxyhotels.admin.feature_auth.domain.use_cases.GetTokenUseCases
import com.oxyhotels.admin.feature_auth.domain.use_cases.SetTokenUseCases
import com.oxyhotels.admin.feature_location.domain.use_cases.ListAllLocationsUseCases
import com.oxyhotels.admin.feature_location.domain.use_cases.LocationUseCases
import com.oxyhotels.admin.feature_location.domain.use_cases.UpdateLocationUseCases
import com.oxyhotels.admin.feature_manage_hotel.data.data_source.HotelDatabase
import com.oxyhotels.admin.feature_manage_hotel.data.repositry.HotelRepositoryImpl
import com.oxyhotels.admin.feature_manage_hotel.domain.repositry.HotelRepository
import com.oxyhotels.admin.feature_manage_hotel.domain.use_cases.AddHotel
import com.oxyhotels.admin.feature_manage_hotel.domain.use_cases.AddHotels
import com.oxyhotels.admin.feature_manage_hotel.domain.use_cases.ClearHotels
import com.oxyhotels.admin.feature_manage_hotel.domain.use_cases.GetHotel
import com.oxyhotels.admin.feature_manage_hotel.domain.use_cases.GetHotelById
import com.oxyhotels.admin.feature_manage_hotel.domain.use_cases.HotelUseCases
import com.oxyhotels.admin.feature_manager.domain.use_cases.ListAllManagerUseCases
import com.oxyhotels.admin.feature_manager.domain.use_cases.ManagerUseCases
import com.oxyhotels.admin.feature_manager.domain.use_cases.UpdateManagerUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(app: Application): SharedPreferences {
        return app.getSharedPreferences("OXY_ADMIN_AUTH_DATA", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun providesAuthDataRepository(sharedPreferences: SharedPreferences): AuthDataRepository {
        return AuthTokenRepositoryImpl(
            sharedPreferences = sharedPreferences
        )
    }

    @Provides
    @Singleton
    fun providesAuthTokenUseCases(authDataRepository: AuthDataRepository): AuthTokenUseCases {
        return AuthTokenUseCases(
            getTokenUseCases = GetTokenUseCases(authDataRepository = authDataRepository),
            setTokenUseCases = SetTokenUseCases(authDataRepository = authDataRepository),
            deleteTokenUseCases = DeleteTokenUseCases(authDataRepository = authDataRepository),
        )
    }


    @Provides
    @Singleton
    fun providesHotelDatabase(app: Application): HotelDatabase {
        return Room.databaseBuilder(
            app,
            HotelDatabase::class.java,
            HotelDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigrationFrom(1).build()
    }


    @Provides
    @Singleton
    fun providesHotelRepository(db: HotelDatabase): HotelRepository {
        return HotelRepositoryImpl(hotelDao = db.hotelDao)
    }

    @Provides
    @Singleton
    fun providesHotelUseCases(hotelRepository: HotelRepository): HotelUseCases {
        return HotelUseCases(
            addHotel = AddHotel(hotelRepository = hotelRepository),
            getHotel = GetHotel(hotelRepository = hotelRepository),
            clearHotels = ClearHotels(hotelRepository = hotelRepository),
            addHotels = AddHotels(hotelRepository = hotelRepository),
            getHotelById = GetHotelById(hotelRepository = hotelRepository)
        )
    }

    @Provides
    @Singleton
    fun providesGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun providesManagerUseCases(gson: Gson): ManagerUseCases {
        return ManagerUseCases(
            listAllManagerUseCases = ListAllManagerUseCases(
                gson = gson
            ),
            updateManagerUseCases = UpdateManagerUseCases(
                gson = gson
            )
        )
    }

    @Provides
    @Singleton
    fun providesLocationUseCases(gson: Gson): LocationUseCases {
        return LocationUseCases(
            listAllLocationsUseCases = ListAllLocationsUseCases(
                gson = gson
            ),
            updateLocationUseCases = UpdateLocationUseCases(
                gson = gson
            )
        )
    }


}