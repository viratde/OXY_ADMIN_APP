package com.oxyhotels.admin.di

import com.oxyhotels.admin.feature_booking.presentation.viewmodels.BookingStoreViewModel
import com.oxyhotels.admin.feature_booking.presentation.viewmodels.CreateBookingViewModel
import com.oxyhotels.admin.feature_booking.presentation.viewmodels.PendingViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface ViewModelFactoryProvider {
    fun bookingStoreViewModelFactoryProvider () : BookingStoreViewModel.Factory

    fun createBookingViewModelFactoryProvider () : CreateBookingViewModel.Factory

    fun createPendingViewModelFactoryProvider() : PendingViewModel.Factory

}