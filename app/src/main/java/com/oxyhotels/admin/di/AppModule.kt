package com.oxyhotels.admin.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.oxyhotels.admin.feature_auth.data.repository.AuthTokenRepositoryImpl
import com.oxyhotels.admin.feature_auth.domain.repository.AuthDataRepository
import com.oxyhotels.admin.feature_auth.domain.use_cases.AuthTokenUseCases
import com.oxyhotels.admin.feature_auth.domain.use_cases.DeleteTokenUseCases
import com.oxyhotels.admin.feature_auth.domain.use_cases.GetTokenUseCases
import com.oxyhotels.admin.feature_auth.domain.use_cases.SetTokenUseCases
import com.oxyhotels.admin.feature_booking.data.data_source.BookingDatabase
import com.oxyhotels.admin.feature_booking.data.repository.BookingRepositoryImpl
import com.oxyhotels.admin.feature_booking.domain.repository.BookingRepository
import com.oxyhotels.admin.feature_booking.domain.use_cases.AddBookingUseCases
import com.oxyhotels.admin.feature_booking.domain.use_cases.BookingUseCases
import com.oxyhotels.admin.feature_booking.domain.use_cases.ClearBookingUseCases
import com.oxyhotels.admin.feature_booking.domain.use_cases.DeleteBookingByBookingId
import com.oxyhotels.admin.feature_booking.domain.use_cases.GetBookingByBookingId
import com.oxyhotels.admin.feature_booking.domain.use_cases.GetBookingsUseCases
import com.oxyhotels.admin.feature_booking.domain.use_cases.UpdateBookingUseCase
import com.oxyhotels.admin.feature_manage_hotel.data.data_source.HotelDatabase
import com.oxyhotels.admin.feature_manage_hotel.data.repositry.HotelRepositoryImpl
import com.oxyhotels.admin.feature_manage_hotel.domain.repositry.HotelRepository
import com.oxyhotels.admin.feature_manage_hotel.domain.use_cases.AddHotel
import com.oxyhotels.admin.feature_manage_hotel.domain.use_cases.AddHotels
import com.oxyhotels.admin.feature_manage_hotel.domain.use_cases.ClearHotels
import com.oxyhotels.admin.feature_manage_hotel.domain.use_cases.GetHotel
import com.oxyhotels.admin.feature_manage_hotel.domain.use_cases.GetHotelById
import com.oxyhotels.admin.feature_manage_hotel.domain.use_cases.HotelUseCases
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
    fun provideSharedPreferences(app:Application) :SharedPreferences{
        return app.getSharedPreferences("OXY_ADMIN_AUTH_DATA",Context.MODE_PRIVATE)
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
    fun providesAuthTokenUseCases(authDataRepository: AuthDataRepository) : AuthTokenUseCases {
        return AuthTokenUseCases(
            getTokenUseCases = GetTokenUseCases(authDataRepository = authDataRepository),
            setTokenUseCases = SetTokenUseCases(authDataRepository = authDataRepository),
            deleteTokenUseCases = DeleteTokenUseCases(authDataRepository = authDataRepository),)
    }

    @Provides
    @Singleton
    fun providesRoomDatabase(app:Application) : BookingDatabase {
        return Room.databaseBuilder(
            app,
            BookingDatabase::class.java,
            BookingDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun providesHotelDatabase(app:Application) : HotelDatabase {
        return Room.databaseBuilder(
            app,
            HotelDatabase::class.java,
            HotelDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun providesBookingRepository(db: BookingDatabase) : BookingRepository {
        return BookingRepositoryImpl(db.bookingDao)
    }

    @Provides
    @Singleton
    fun providesHotelRepository(db:HotelDatabase): HotelRepository {
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
    fun providesBookingUseCases(bookingRepository: BookingRepository) : BookingUseCases {
        return BookingUseCases(
            getBookingsUseCases = GetBookingsUseCases(bookingRepository = bookingRepository),
            addBookingUseCases = AddBookingUseCases(bookingRepository = bookingRepository),
            clearBookingUseCases = ClearBookingUseCases(bookingRepository = bookingRepository),
            deleteBookingByBookingId = DeleteBookingByBookingId(bookingRepository = bookingRepository),
            updateBookingUseCase = UpdateBookingUseCase(bookingRepository = bookingRepository),
            getBookingByBookingId = GetBookingByBookingId(bookingRepository = bookingRepository)
        )
    }

}